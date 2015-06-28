ReadMyTables provides a common Java interface for accessing tabular data across different data sources eg: delimited files, Excel files etc.

A table consists of a header and multiple rows, with each column consisting of values of the same Java type.

Through the use of [CellReaders](http://readmytables.googlecode.com/git/readmytables/doc/org/omancode/rmt/cellreader/package-summary.html), ReadMyTables will produce typed Java objects (String, Character, Boolean, Integer, Long etc.) from untyped tabular data.

Includes:
  * [ReadMyTablesFromFiles](http://readmytables.googlecode.com/git/readmytablesfromfiles/doc/index.html) - implementations for reading tables from delimited (eg: CSV) text files and Excel (XLS/XLSX) files.
  * [utils for narrowing objects](http://readmytables.googlecode.com/git/readmytables/doc/org/omancode/rmt/cellreader/narrow/package-summary.html), ie: converting them to the smallest possible type without losing fidelity
  * support for handling missing cell values