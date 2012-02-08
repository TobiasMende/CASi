#!/bin/bash
for file in *.aux ; do
  /usr/local/texlive/2010/bin/x86_64-darwin/bibtex `basename $file .aux`
done
