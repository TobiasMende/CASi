/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2011 2012  Moritz Bürger, Marvin Frick, Tobias Mende
 *
 *  This program is free software. It is licensed under the
 *  GNU Lesser General Public License with one clarification.
 *  
 *  You should have received a copy of the 
 *  GNU Lesser General Public License along with this program. 
 *  See the LICENSE.txt file in this projects root folder or visit
 *  <http://www.gnu.org/licenses/lgpl.html> for more details.
 */
package de.uniluebeck.imis.casi.simulation.model.actionHandling;

import java.io.Serializable;
import java.util.Comparator;
import java.util.TreeSet;

import de.uniluebeck.imis.casi.simulation.model.actionHandling.schedulers.DefaultActionScheduler;

/**
 * The action comparator is able to compare two actions as needed for scheduling
 * e.g by the {@link DefaultActionScheduler}.
 * 
 * <p>
 * <strong>This comparator compares as described below:</strong><br>
 * <ol>
 * <li>compare deadlines, earliest deadline is lower
 * <li>if deadlines are equal and not null, compare the priorities, higher
 * priority results in a lower result
 * <li>if priorities are equal, compare the latest time when the actions could
 * be performed to finish before their deadline, lower latest time results in
 * lower result
 * <li>if no decision until here, compare start times. Actions without start
 * time are rated lower because they could be performed whenever we like to
 * perform them.<br>
 * Actions with an earlier earliest start time are rated lower because we are
 * able to start them earlier (Sounds logical eeh? :-) )
 * <li>if no decision we compare the priorities again as a last try.
 * <li>last but not least if we haven't found any differences until here, we
 * check if the actions are really equal to avoid rating them as equal if they
 * arn't because {@link TreeSet}s would forget one action.
 * <ol>
 * </p>
 * 
 * @author Tobias Mende
 * 
 */
public class ActionComparator implements Comparator<AbstractAction>, Serializable {

	/**
	 * Serialization identifier
	 */
	private static final long serialVersionUID = -3767832458193826199L;

	@Override
	public int compare(AbstractAction first, AbstractAction second) {
		if(first == null && second == null) {
			return 0;
		} else if(first == null) {
			return -1;
		} else if(second == null) {
			return 1;
		}
		int deadlineCompare = compareDeadlines(first, second);
		if (deadlineCompare != 0) {
			// Earliest deadline first:
			return deadlineCompare;
		}
		/*
		 * Deadlines don't help while ordering actions: Use latest execution
		 * time by looking at duration and deadline.
		 */
		int latestExectutionTimeCompare = compareLatestExecutionTimes(first,
				second);
		if (latestExectutionTimeCompare != 0) {
			// Made decision:
			return latestExectutionTimeCompare;
		}
		/*
		 * Haven't made a good decision until here. Try comparing the earliest
		 * start times:
		 */
		int startTimeCompare = compareEarliestStartTimes(first, second);
		if (startTimeCompare != 0) {
			return startTimeCompare;
		}
		/*
		 * They seem to be totally equal. Last try: Priorities... If we don't
		 * get a good decision with that, it's the scheduler's problem ;-)
		 */
		int priorityCompare = comparePriorities(first, second);
		if (priorityCompare != 0) {
			return priorityCompare;
		}
		/*
		 * In rare cases we can't find a difference in times. TreeSets would
		 * deemed this actions as equal and forget one. This shouldn't happen,
		 * so verify that they are really equal as last case:
		 */
		return first.equals(second) ? 0 : -1;
	}

	/**
	 * Compares the earliest start times so that actions without start times are
	 * moved to front and actions with a lower start time are rated lower.
	 * 
	 * @param first
	 *            the first action
	 * @param second
	 *            the second action
	 * @return an integer lesser, equal or higher than zero if the first
	 *         argument is lower, equal or higher than the second.
	 */
	private int compareEarliestStartTimes(AbstractAction first,
			AbstractAction second) {
		if (first.getEarliestStartTime() == null
				&& second.getEarliestStartTime() == null) {
			// Can't get a decision by using the start times:
			return 0;
		}
		if (first.getEarliestStartTime() == null) {
			/*
			 * Second has an earliest start time. The first not. We can perform
			 * the fist action as early as we like to. move to front:
			 */
			return -1;
		}

		if (second.getEarliestStartTime() == null) {
			/*
			 * First action has an earliest start time. The second not. We can
			 * perform the second action as early as we like to. move to front:
			 */
			return 1;
		}
		// Compare the start time itself.
		return first.getEarliestStartTime().compareTo(
				second.getEarliestStartTime());
	}

	/**
	 * Compares the times when actions could be performs the latest to finish
	 * before their deadlines according to their duration and their deadline.
	 * 
	 * @param first
	 *            the first action
	 * @param second
	 *            the second action
	 * @return an integer lesser, equal or higher than zero if the first
	 *         argument is lower, equal or higher than the second.
	 */
	private int compareLatestExecutionTimes(AbstractAction first,
			AbstractAction second) {
		if (first.getDeadline() == null || second.getDeadline() == null) {
			// Duration not useful in this case:
			return 0;
		}
		if (first.getDuration() == -1 || second.getDuration() == -1) {
			// Duration not useful for comparison:
			return 0;
		}
		// Calculate the latest time when the actions could be performed to
		// complete before their deadline:
		int firstExecutionTime = (int) (first.getDeadline().getTime())
				- (int) first.getDurationSeconds() * 1000;
		int secondExecutionTime = (int) (second.getDeadline().getTime())
				- (int) second.getDurationSeconds() * 1000;
		int difference = firstExecutionTime - secondExecutionTime;
		// Return difference if not zero or compare the priorities for a better
		// decision:
		return difference != 0 ? difference : comparePriorities(first, second);
	}

	/**
	 * Compares the deadlines as needed for EDF. If deadlines are equal, this
	 * method also uses the priorities for comparison. <br>
	 * Using this method in a collection of actions will result in an ordered
	 * collection so that elements with the earliest deadline are at front and
	 * actions with no deadline are at the end.
	 * 
	 * @param first
	 *            the first action
	 * @param second
	 *            the second action
	 * @return an integer lesser, equal or higher than zero if the first
	 *         argument is lower, equal or higher than the second.
	 */
	private int compareDeadlines(AbstractAction first, AbstractAction second) {
		if (first.getDeadline() == null && second.getDeadline() == null) {
			// No deadlines set. Can't say anything about them
			return 0;
		}
		if (first.getDeadline() == null) {
			// second action has a deadline, so it must be performed earlier
			// (higher position needed)
			return 1;
		}
		if (second.getDeadline() == null) {
			// first action has a deadline, so it must be performed earlier
			// (higher position needed)
			return -1;
		}
		// See which deadline is earlier
		int compare = first.getDeadline().compareTo(second.getDeadline());
		if (compare != 0) {
			return compare;
		}
		// Equal deadlines, uses priorities
		return comparePriorities(first, second);
	}

	/**
	 * Compares the priorities of two actions. a higher priority of the first
	 * action results in a lower return value
	 * 
	 * @param first
	 *            the first action
	 * @param second
	 *            the second action
	 * @return an integer lesser, equal or higher than zero if the first actions
	 *         priority is higher, equal or lower than the second ones.
	 */
	private int comparePriorities(AbstractAction first, AbstractAction second) {
		return second.getPriority() - first.getPriority();
	}

}
