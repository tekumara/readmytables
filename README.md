ReadMyTables provides a common Java interface for accessing tabular data across different data sources eg: delimited files, Excel files etc.

A table consists of a header and multiple rows, with each column consisting of values of the same Java type. 

Through the use of CellReaders, ReadMyTables will produce typed Java objects (String, Character, Boolean, Integer, Long etc.) from untyped tabular data.

Includes:
  * ReadMyTablesFromFiles - implementations for reading tables from delimited (eg: CSV) text files and Excel (XLS/XLSX) files.
  * Utils for narrowing objects, ie: converting them to the smallest possible type without losing fidelity
  * Support for handling missing cell values
