package org.omancode.rmt.tablereader.file;

import org.supercsv.prefs.CsvPreference;

/**
 * Specifies the quote, delimiter, and end of line character for a delimited
 * file.
 * 
 * @author Oliver Mannion
 * @version $Revision: 54 $
 */
public class DelimiterSettings {

	/**
	 * Windows CSV file settings. Quote char: "<br>
	 * Delimiter: ,<br>
	 * End of line: \r\n
	 */
	public static final DelimiterSettings WINDOWS_CSV = new DelimiterSettings(
			'"', ',', "\r\n");

	private final char quote;
	private final char delimiter;
	private final String endOfLine;

	/**
	 * Constructor.
	 * 
	 * @param quote
	 *            quote char, eg: "
	 * @param delimiter
	 *            delimiter, eg: ,
	 * @param endOfLine
	 *            end of line, eg: \r\n
	 */
	public DelimiterSettings(char quote, char delimiter, String endOfLine) {
		this.quote = quote;
		this.delimiter = delimiter;
		this.endOfLine = endOfLine;
	}

	/**
	 * Convert to SuperCsv {@link CsvPreference} object.
	 * 
	 * @return CsvPreference object representing this delimiter setting.
	 */
	public CsvPreference getCsvPreference() {
		return new CsvPreference(this.quote, this.delimiter, this.endOfLine);

	}

}
