/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mini.core.extension

import org.apache.commons.lang3.ArrayUtils
import org.apache.commons.lang3.CharSequenceUtils
import org.apache.commons.lang3.CharUtils
import org.apache.commons.lang3.RegExUtils
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.text.Normalizer
import java.util.*
import java.util.regex.Pattern

/**
 *
 * Operations on [String] that are
 * `null` safe.
 *
 *
 *  * **IsEmpty/IsBlank**
 * - checks if a String contains text
 *  * **Trim/Strip**
 * - removes leading and trailing whitespace
 *  * **Equals/Compare**
 * - compares two strings null-safe
 *  * **startsWith**
 * - check if a String starts with a prefix null-safe
 *  * **endsWith**
 * - check if a String ends with a suffix null-safe
 *  * **IndexOf/LastIndexOf/Contains**
 * - null-safe index-of checks
 *  * **IndexOfAny/LastIndexOfAny/IndexOfAnyBut/LastIndexOfAnyBut**
 * - index-of any of a set of Strings
 *  * **ContainsOnly/ContainsNone/ContainsAny**
 * - does String contains only/none/any of these characters
 *  * **Substring/Left/Right/Mid**
 * - null-safe substring extractions
 *  * **SubstringBefore/SubstringAfter/SubstringBetween**
 * - substring extraction relative to other strings
 *  * **Split/Join**
 * - splits a String into an array of substrings and vice versa
 *  * **Remove/Delete**
 * - removes part of a String
 *  * **Replace/Overlay**
 * - Searches a String and replaces one String with another
 *  * **Chomp/Chop**
 * - removes the last part of a String
 *  * **AppendIfMissing**
 * - appends a suffix to the end of the String if not present
 *  * **PrependIfMissing**
 * - prepends a prefix to the start of the String if not present
 *  * **LeftPad/RightPad/Center/Repeat**
 * - pads a String
 *  * **UpperCase/LowerCase/SwapCase/Capitalize/Uncapitalize**
 * - changes the case of a String
 *  * **CountMatches**
 * - counts the number of occurrences of one String in another
 *  * **IsAlpha/IsNumeric/IsWhitespace/IsAsciiPrintable**
 * - checks the characters in a String
 *  * **DefaultString**
 * - protects against a null input String
 *  * **Rotate**
 * - rotate (circular shift) a String
 *  * **Reverse/ReverseDelimited**
 * - reverses a String
 *  * **Abbreviate**
 * - abbreviates a string using ellipsis or another given String
 *  * **Difference**
 * - compares Strings and reports on their differences
 *  * **LevenshteinDistance**
 * - the number of changes needed to change one String into another
 *
 *
 *
 * The `StringUtils` class defines certain words related to
 * String handling.
 *
 *
 *  * null - `null`
 *  * empty - a zero-length string (`""`)
 *  * space - the space character (`' '`, char 32)
 *  * whitespace - the characters defined by [Character.isWhitespace]
 *  * trim - the characters &lt;= 32 as in [String.trim]
 *
 *
 *
 * `StringUtils` handles `null` input Strings quietly.
 * That is to say that a `null` input will return `null`.
 * Where a `boolean` or `int` is being returned
 * details vary by method.
 *
 *
 * A side effect of the `null` handling is that a
 * `NullPointerException` should be considered a bug in
 * `StringUtils`.
 *
 *
 * Methods in this class give sample code to explain their operation.
 * The symbol `*` is used to indicate any input including `null`.
 *
 *
 * #ThreadSafe#
 * @see String
 *
 * @since 1.0
 */
//@Immutable
object StringUtils {
    private const val STRING_BUILDER_SIZE = 256
    // Performance testing notes (JDK 1.4, Jul03, scolebourne)
// Whitespace:
// Character.isWhitespace() is faster than WHITESPACE.indexOf()
// where WHITESPACE is a string of all whitespace characters
//
// Character access:
// String.charAt(n) versus toCharArray(), then array[n]
// String.charAt(n) is about 15% worse for a 10K string
// They are about equal for a length 50 string
// String.charAt(n) is about 4 times better for a length 3 string
// String.charAt(n) is best bet overall
//
// Append:
// String.concat about twice as fast as StringBuffer.append
// (not sure who tested this)
    /**
     * A String for a space character.
     *
     * @since 3.2
     */
    const val SPACE = " "
    /**
     * The empty String `""`.
     * @since 2.0
     */
    const val EMPTY = ""
    /**
     * A String for linefeed LF ("\n").
     *
     * @see [JLF: Escape Sequences
     * for Character and String Literals](http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html.jls-3.10.6)
     *
     * @since 3.2
     */
    const val LF = "\n"
    /**
     * A String for carriage return CR ("\r").
     *
     * @see [JLF: Escape Sequences
     * for Character and String Literals](http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html.jls-3.10.6)
     *
     * @since 3.2
     */
    const val CR = "\r"
    /**
     * Represents a failed index search.
     * @since 2.1
     */
    const val INDEX_NOT_FOUND = -1
    /**
     *
     * The maximum size to which the padding constant(s) can expand.
     */
    private const val PAD_LIMIT = 8192
    // Empty checks
//-----------------------------------------------------------------------
    /**
     *
     * Checks if a CharSequence is empty ("") or null.
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
    </pre> *
     *
     *
     * NOTE: This method changed in Lang version 2.0.
     * It no longer trims the CharSequence.
     * That functionality is available in isBlank().
     *
     * @param cs  the CharSequence to check, may be null
     * @return `true` if the CharSequence is empty or null
     * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
     */
    fun isEmpty(cs: CharSequence?): Boolean {
        return cs == null || cs.length == 0
    }

    /**
     *
     * Checks if a CharSequence is not empty ("") and not null.
     *
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @return `true` if the CharSequence is not empty and not null
     * @since 3.0 Changed signature from isNotEmpty(String) to isNotEmpty(CharSequence)
     */
    fun isNotEmpty(cs: CharSequence?): Boolean {
        return !isEmpty(cs)
    }

    /**
     *
     * Checks if any of the CharSequences are empty ("") or null.
     *
     * <pre>
     * StringUtils.isAnyEmpty((String) null)    = true
     * StringUtils.isAnyEmpty((String[]) null)  = false
     * StringUtils.isAnyEmpty(null, "foo")      = true
     * StringUtils.isAnyEmpty("", "bar")        = true
     * StringUtils.isAnyEmpty("bob", "")        = true
     * StringUtils.isAnyEmpty("  bob  ", null)  = true
     * StringUtils.isAnyEmpty(" ", "bar")       = false
     * StringUtils.isAnyEmpty("foo", "bar")     = false
     * StringUtils.isAnyEmpty(new String[]{})   = false
     * StringUtils.isAnyEmpty(new String[]{""}) = true
    </pre> *
     *
     * @param css  the CharSequences to check, may be null or empty
     * @return `true` if any of the CharSequences are empty or null
     * @since 3.2
     */
    fun isAnyEmpty(vararg css: CharSequence?): Boolean {
        if (ArrayUtils.isEmpty(css)) {
            return false
        }
        for (cs in css) {
            if (isEmpty(cs)) {
                return true
            }
        }
        return false
    }

    /**
     *
     * Checks if none of the CharSequences are empty ("") or null.
     *
     * <pre>
     * StringUtils.isNoneEmpty((String) null)    = false
     * StringUtils.isNoneEmpty((String[]) null)  = true
     * StringUtils.isNoneEmpty(null, "foo")      = false
     * StringUtils.isNoneEmpty("", "bar")        = false
     * StringUtils.isNoneEmpty("bob", "")        = false
     * StringUtils.isNoneEmpty("  bob  ", null)  = false
     * StringUtils.isNoneEmpty(new String[] {})  = true
     * StringUtils.isNoneEmpty(new String[]{""}) = false
     * StringUtils.isNoneEmpty(" ", "bar")       = true
     * StringUtils.isNoneEmpty("foo", "bar")     = true
    </pre> *
     *
     * @param css  the CharSequences to check, may be null or empty
     * @return `true` if none of the CharSequences are empty or null
     * @since 3.2
     */
    fun isNoneEmpty(vararg css: CharSequence?): Boolean {
        return !isAnyEmpty(*css)
    }

    /**
     *
     * Checks if all of the CharSequences are empty ("") or null.
     *
     * <pre>
     * StringUtils.isAllEmpty(null)             = true
     * StringUtils.isAllEmpty(null, "")         = true
     * StringUtils.isAllEmpty(new String[] {})  = true
     * StringUtils.isAllEmpty(null, "foo")      = false
     * StringUtils.isAllEmpty("", "bar")        = false
     * StringUtils.isAllEmpty("bob", "")        = false
     * StringUtils.isAllEmpty("  bob  ", null)  = false
     * StringUtils.isAllEmpty(" ", "bar")       = false
     * StringUtils.isAllEmpty("foo", "bar")     = false
    </pre> *
     *
     * @param css  the CharSequences to check, may be null or empty
     * @return `true` if all of the CharSequences are empty or null
     * @since 3.6
     */
    fun isAllEmpty(vararg css: CharSequence?): Boolean {
        if (ArrayUtils.isEmpty(css)) {
            return true
        }
        for (cs in css) {
            if (isNotEmpty(cs)) {
                return false
            }
        }
        return true
    }

    /**
     *
     * Checks if a CharSequence is empty (""), null or whitespace only.
     *
     *
     * Whitespace is defined by [Character.isWhitespace].
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @return `true` if the CharSequence is null, empty or whitespace only
     * @since 2.0
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    fun isBlank(cs: CharSequence?): Boolean {
        var strLen: Int
        if (cs == null || cs.length.also { strLen = it } == 0) {
            return true
        }
        for (i in 0 until strLen) {
            if (!Character.isWhitespace(cs[i])) {
                return false
            }
        }
        return true
    }

    /**
     *
     * Checks if a CharSequence is not empty (""), not null and not whitespace only.
     *
     *
     * Whitespace is defined by [Character.isWhitespace].
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @return `true` if the CharSequence is
     * not empty and not null and not whitespace only
     * @since 2.0
     * @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
     */
    fun isNotBlank(cs: CharSequence?): Boolean {
        return !isBlank(cs)
    }

    /**
     *
     * Checks if any of the CharSequences are empty ("") or null or whitespace only.
     *
     *
     * Whitespace is defined by [Character.isWhitespace].
     *
     * <pre>
     * StringUtils.isAnyBlank((String) null)    = true
     * StringUtils.isAnyBlank((String[]) null)  = false
     * StringUtils.isAnyBlank(null, "foo")      = true
     * StringUtils.isAnyBlank(null, null)       = true
     * StringUtils.isAnyBlank("", "bar")        = true
     * StringUtils.isAnyBlank("bob", "")        = true
     * StringUtils.isAnyBlank("  bob  ", null)  = true
     * StringUtils.isAnyBlank(" ", "bar")       = true
     * StringUtils.isAnyBlank(new String[] {})  = false
     * StringUtils.isAnyBlank(new String[]{""}) = true
     * StringUtils.isAnyBlank("foo", "bar")     = false
    </pre> *
     *
     * @param css  the CharSequences to check, may be null or empty
     * @return `true` if any of the CharSequences are empty or null or whitespace only
     * @since 3.2
     */
    fun isAnyBlank(vararg css: CharSequence?): Boolean {
        if (ArrayUtils.isEmpty(css)) {
            return false
        }
        for (cs in css) {
            if (isBlank(cs)) {
                return true
            }
        }
        return false
    }

    /**
     *
     * Checks if none of the CharSequences are empty (""), null or whitespace only.
     *
     *
     * Whitespace is defined by [Character.isWhitespace].
     *
     * <pre>
     * StringUtils.isNoneBlank((String) null)    = false
     * StringUtils.isNoneBlank((String[]) null)  = true
     * StringUtils.isNoneBlank(null, "foo")      = false
     * StringUtils.isNoneBlank(null, null)       = false
     * StringUtils.isNoneBlank("", "bar")        = false
     * StringUtils.isNoneBlank("bob", "")        = false
     * StringUtils.isNoneBlank("  bob  ", null)  = false
     * StringUtils.isNoneBlank(" ", "bar")       = false
     * StringUtils.isNoneBlank(new String[] {})  = true
     * StringUtils.isNoneBlank(new String[]{""}) = false
     * StringUtils.isNoneBlank("foo", "bar")     = true
    </pre> *
     *
     * @param css  the CharSequences to check, may be null or empty
     * @return `true` if none of the CharSequences are empty or null or whitespace only
     * @since 3.2
     */
    fun isNoneBlank(vararg css: CharSequence?): Boolean {
        return !isAnyBlank(*css)
    }

    /**
     *
     * Checks if all of the CharSequences are empty (""), null or whitespace only.
     *
     *
     * Whitespace is defined by [Character.isWhitespace].
     *
     * <pre>
     * StringUtils.isAllBlank(null)             = true
     * StringUtils.isAllBlank(null, "foo")      = false
     * StringUtils.isAllBlank(null, null)       = true
     * StringUtils.isAllBlank("", "bar")        = false
     * StringUtils.isAllBlank("bob", "")        = false
     * StringUtils.isAllBlank("  bob  ", null)  = false
     * StringUtils.isAllBlank(" ", "bar")       = false
     * StringUtils.isAllBlank("foo", "bar")     = false
     * StringUtils.isAllBlank(new String[] {})  = true
    </pre> *
     *
     * @param css  the CharSequences to check, may be null or empty
     * @return `true` if all of the CharSequences are empty or null or whitespace only
     * @since 3.6
     */
    fun isAllBlank(vararg css: CharSequence?): Boolean {
        if (ArrayUtils.isEmpty(css)) {
            return true
        }
        for (cs in css) {
            if (isNotBlank(cs)) {
                return false
            }
        }
        return true
    }
    // Trim
//-----------------------------------------------------------------------
    /**
     *
     * Removes control characters (char &lt;= 32) from both
     * ends of this String, handling `null` by returning
     * `null`.
     *
     *
     * The String is trimmed using [String.trim].
     * Trim removes start and end characters &lt;= 32.
     * To strip whitespace use [.strip].
     *
     *
     * To trim your choice of characters, use the
     * [.strip] methods.
     *
     * <pre>
     * StringUtils.trim(null)          = null
     * StringUtils.trim("")            = ""
     * StringUtils.trim("     ")       = ""
     * StringUtils.trim("abc")         = "abc"
     * StringUtils.trim("    abc    ") = "abc"
    </pre> *
     *
     * @param str  the String to be trimmed, may be null
     * @return the trimmed string, `null` if null String input
     */
    fun trim(str: String?): String? {
        return str?.trim { it <= ' ' }
    }

    /**
     *
     * Removes control characters (char &lt;= 32) from both
     * ends of this String returning `null` if the String is
     * empty ("") after the trim or if it is `null`.
     *
     *
     * The String is trimmed using [String.trim].
     * Trim removes start and end characters &lt;= 32.
     * To strip whitespace use [.stripToNull].
     *
     * <pre>
     * StringUtils.trimToNull(null)          = null
     * StringUtils.trimToNull("")            = null
     * StringUtils.trimToNull("     ")       = null
     * StringUtils.trimToNull("abc")         = "abc"
     * StringUtils.trimToNull("    abc    ") = "abc"
    </pre> *
     *
     * @param str  the String to be trimmed, may be null
     * @return the trimmed String,
     * `null` if only chars &lt;= 32, empty or null String input
     * @since 2.0
     */
    fun trimToNull(str: String?): String? {
        val ts = trim(str)
        return if (isEmpty(ts)) null else ts
    }

    /**
     *
     * Removes control characters (char &lt;= 32) from both
     * ends of this String returning an empty String ("") if the String
     * is empty ("") after the trim or if it is `null`.
     *
     *
     * The String is trimmed using [String.trim].
     * Trim removes start and end characters &lt;= 32.
     * To strip whitespace use [.stripToEmpty].
     *
     * <pre>
     * StringUtils.trimToEmpty(null)          = ""
     * StringUtils.trimToEmpty("")            = ""
     * StringUtils.trimToEmpty("     ")       = ""
     * StringUtils.trimToEmpty("abc")         = "abc"
     * StringUtils.trimToEmpty("    abc    ") = "abc"
    </pre> *
     *
     * @param str  the String to be trimmed, may be null
     * @return the trimmed String, or an empty String if `null` input
     * @since 2.0
     */
    fun trimToEmpty(str: String?): String {
        return str?.trim { it <= ' ' } ?: EMPTY
    }

    /**
     *
     * Truncates a String. This will turn
     * "Now is the time for all good men" into "Now is the time for".
     *
     *
     * Specifically:
     *
     *  * If `str` is less than `maxWidth` characters
     * long, return it.
     *  * Else truncate it to `substring(str, 0, maxWidth)`.
     *  * If `maxWidth` is less than `0`, throw an
     * `IllegalArgumentException`.
     *  * In no case will it return a String of length greater than
     * `maxWidth`.
     *
     *
     * <pre>
     * StringUtils.truncate(null, 0)       = null
     * StringUtils.truncate(null, 2)       = null
     * StringUtils.truncate("", 4)         = ""
     * StringUtils.truncate("abcdefg", 4)  = "abcd"
     * StringUtils.truncate("abcdefg", 6)  = "abcdef"
     * StringUtils.truncate("abcdefg", 7)  = "abcdefg"
     * StringUtils.truncate("abcdefg", 8)  = "abcdefg"
     * StringUtils.truncate("abcdefg", -1) = throws an IllegalArgumentException
    </pre> *
     *
     * @param str  the String to truncate, may be null
     * @param maxWidth  maximum length of result String, must be positive
     * @return truncated String, `null` if null String input
     * @since 3.5
     */
    fun truncate(str: String?, maxWidth: Int): String? {
        return truncate(str, 0, maxWidth)
    }

    /**
     *
     * Truncates a String. This will turn
     * "Now is the time for all good men" into "is the time for all".
     *
     *
     * Works like `truncate(String, int)`, but allows you to specify
     * a "left edge" offset.
     *
     *
     * Specifically:
     *
     *  * If `str` is less than `maxWidth` characters
     * long, return it.
     *  * Else truncate it to `substring(str, offset, maxWidth)`.
     *  * If `maxWidth` is less than `0`, throw an
     * `IllegalArgumentException`.
     *  * If `offset` is less than `0`, throw an
     * `IllegalArgumentException`.
     *  * In no case will it return a String of length greater than
     * `maxWidth`.
     *
     *
     * <pre>
     * StringUtils.truncate(null, 0, 0) = null
     * StringUtils.truncate(null, 2, 4) = null
     * StringUtils.truncate("", 0, 10) = ""
     * StringUtils.truncate("", 2, 10) = ""
     * StringUtils.truncate("abcdefghij", 0, 3) = "abc"
     * StringUtils.truncate("abcdefghij", 5, 6) = "fghij"
     * StringUtils.truncate("raspberry peach", 10, 15) = "peach"
     * StringUtils.truncate("abcdefghijklmno", 0, 10) = "abcdefghij"
     * StringUtils.truncate("abcdefghijklmno", -1, 10) = throws an IllegalArgumentException
     * StringUtils.truncate("abcdefghijklmno", Integer.MIN_VALUE, 10) = "abcdefghij"
     * StringUtils.truncate("abcdefghijklmno", Integer.MIN_VALUE, Integer.MAX_VALUE) = "abcdefghijklmno"
     * StringUtils.truncate("abcdefghijklmno", 0, Integer.MAX_VALUE) = "abcdefghijklmno"
     * StringUtils.truncate("abcdefghijklmno", 1, 10) = "bcdefghijk"
     * StringUtils.truncate("abcdefghijklmno", 2, 10) = "cdefghijkl"
     * StringUtils.truncate("abcdefghijklmno", 3, 10) = "defghijklm"
     * StringUtils.truncate("abcdefghijklmno", 4, 10) = "efghijklmn"
     * StringUtils.truncate("abcdefghijklmno", 5, 10) = "fghijklmno"
     * StringUtils.truncate("abcdefghijklmno", 5, 5) = "fghij"
     * StringUtils.truncate("abcdefghijklmno", 5, 3) = "fgh"
     * StringUtils.truncate("abcdefghijklmno", 10, 3) = "klm"
     * StringUtils.truncate("abcdefghijklmno", 10, Integer.MAX_VALUE) = "klmno"
     * StringUtils.truncate("abcdefghijklmno", 13, 1) = "n"
     * StringUtils.truncate("abcdefghijklmno", 13, Integer.MAX_VALUE) = "no"
     * StringUtils.truncate("abcdefghijklmno", 14, 1) = "o"
     * StringUtils.truncate("abcdefghijklmno", 14, Integer.MAX_VALUE) = "o"
     * StringUtils.truncate("abcdefghijklmno", 15, 1) = ""
     * StringUtils.truncate("abcdefghijklmno", 15, Integer.MAX_VALUE) = ""
     * StringUtils.truncate("abcdefghijklmno", Integer.MAX_VALUE, Integer.MAX_VALUE) = ""
     * StringUtils.truncate("abcdefghij", 3, -1) = throws an IllegalArgumentException
     * StringUtils.truncate("abcdefghij", -2, 4) = throws an IllegalArgumentException
    </pre> *
     *
     * @param str  the String to check, may be null
     * @param offset  left edge of source String
     * @param maxWidth  maximum length of result String, must be positive
     * @return truncated String, `null` if null String input
     * @since 3.5
     */
    fun truncate(str: String?, offset: Int, maxWidth: Int): String? {
        require(offset >= 0) { "offset cannot be negative" }
        require(maxWidth >= 0) { "maxWith cannot be negative" }
        if (str == null) {
            return null
        }
        if (offset > str.length) {
            return EMPTY
        }
        if (str.length > maxWidth) {
            val ix = if (offset + maxWidth > str.length) str.length else offset + maxWidth
            return str.substring(offset, ix)
        }
        return str.substring(offset)
    }

    /**
     *
     * Strips whitespace from the start and end of a String  returning
     * `null` if the String is empty ("") after the strip.
     *
     *
     * This is similar to [.trimToNull] but removes whitespace.
     * Whitespace is defined by [Character.isWhitespace].
     *
     * <pre>
     * StringUtils.stripToNull(null)     = null
     * StringUtils.stripToNull("")       = null
     * StringUtils.stripToNull("   ")    = null
     * StringUtils.stripToNull("abc")    = "abc"
     * StringUtils.stripToNull("  abc")  = "abc"
     * StringUtils.stripToNull("abc  ")  = "abc"
     * StringUtils.stripToNull(" abc ")  = "abc"
     * StringUtils.stripToNull(" ab c ") = "ab c"
    </pre> *
     *
     * @param str  the String to be stripped, may be null
     * @return the stripped String,
     * `null` if whitespace, empty or null String input
     * @since 2.0
     */
    fun stripToNull(str: String?): String? {
        var str = str ?: return null
        str = strip(str, null)
        return if (str.isEmpty()) null else str
    }

    /**
     *
     * Strips whitespace from the start and end of a String  returning
     * an empty String if `null` input.
     *
     *
     * This is similar to [.trimToEmpty] but removes whitespace.
     * Whitespace is defined by [Character.isWhitespace].
     *
     * <pre>
     * StringUtils.stripToEmpty(null)     = ""
     * StringUtils.stripToEmpty("")       = ""
     * StringUtils.stripToEmpty("   ")    = ""
     * StringUtils.stripToEmpty("abc")    = "abc"
     * StringUtils.stripToEmpty("  abc")  = "abc"
     * StringUtils.stripToEmpty("abc  ")  = "abc"
     * StringUtils.stripToEmpty(" abc ")  = "abc"
     * StringUtils.stripToEmpty(" ab c ") = "ab c"
    </pre> *
     *
     * @param str  the String to be stripped, may be null
     * @return the trimmed String, or an empty String if `null` input
     * @since 2.0
     */
    fun stripToEmpty(str: String?): String {
        return if (str == null) EMPTY else strip(str, null)
    }
    /**
     *
     * Strips any of a set of characters from the start and end of a String.
     * This is similar to [String.trim] but allows the characters
     * to be stripped to be controlled.
     *
     *
     * A `null` input String returns `null`.
     * An empty string ("") input returns the empty string.
     *
     *
     * If the stripChars String is `null`, whitespace is
     * stripped as defined by [Character.isWhitespace].
     * Alternatively use [.strip].
     *
     * <pre>
     * StringUtils.strip(null, *)          = null
     * StringUtils.strip("", *)            = ""
     * StringUtils.strip("abc", null)      = "abc"
     * StringUtils.strip("  abc", null)    = "abc"
     * StringUtils.strip("abc  ", null)    = "abc"
     * StringUtils.strip(" abc ", null)    = "abc"
     * StringUtils.strip("  abcyx", "xyz") = "  abc"
    </pre> *
     *
     * @param str  the String to remove characters from, may be null
     * @param stripChars  the characters to remove, null treated as whitespace
     * @return the stripped String, `null` if null String input
     */
// Stripping
//-----------------------------------------------------------------------
    /**
     *
     * Strips whitespace from the start and end of a String.
     *
     *
     * This is similar to [.trim] but removes whitespace.
     * Whitespace is defined by [Character.isWhitespace].
     *
     *
     * A `null` input String returns `null`.
     *
     * <pre>
     * StringUtils.strip(null)     = null
     * StringUtils.strip("")       = ""
     * StringUtils.strip("   ")    = ""
     * StringUtils.strip("abc")    = "abc"
     * StringUtils.strip("  abc")  = "abc"
     * StringUtils.strip("abc  ")  = "abc"
     * StringUtils.strip(" abc ")  = "abc"
     * StringUtils.strip(" ab c ") = "ab c"
    </pre> *
     *
     * @param str  the String to remove whitespace from, may be null
     * @return the stripped String, `null` if null String input
     */
    @JvmOverloads
    fun strip(str: String, stripChars: String? = null): String {
        var str = str
        if (isEmpty(str)) {
            return str
        }
        str = stripStart(str, stripChars)
        return stripEnd(str, stripChars)
    }

    /**
     *
     * Strips any of a set of characters from the start of a String.
     *
     *
     * A `null` input String returns `null`.
     * An empty string ("") input returns the empty string.
     *
     *
     * If the stripChars String is `null`, whitespace is
     * stripped as defined by [Character.isWhitespace].
     *
     * <pre>
     * StringUtils.stripStart(null, *)          = null
     * StringUtils.stripStart("", *)            = ""
     * StringUtils.stripStart("abc", "")        = "abc"
     * StringUtils.stripStart("abc", null)      = "abc"
     * StringUtils.stripStart("  abc", null)    = "abc"
     * StringUtils.stripStart("abc  ", null)    = "abc  "
     * StringUtils.stripStart(" abc ", null)    = "abc "
     * StringUtils.stripStart("yxabc  ", "xyz") = "abc  "
    </pre> *
     *
     * @param str  the String to remove characters from, may be null
     * @param stripChars  the characters to remove, null treated as whitespace
     * @return the stripped String, `null` if null String input
     */
    fun stripStart(str: String, stripChars: String?): String {
        var strLen: Int
        if (str == null || str.length.also { strLen = it } == 0) {
            return str
        }
        var start = 0
        if (stripChars == null) {
            while (start != strLen && Character.isWhitespace(str[start])) {
                start++
            }
        } else if (stripChars.isEmpty()) {
            return str
        } else {
            while (start != strLen && stripChars.indexOf(str[start]) != INDEX_NOT_FOUND) {
                start++
            }
        }
        return str.substring(start)
    }

    /**
     *
     * Strips any of a set of characters from the end of a String.
     *
     *
     * A `null` input String returns `null`.
     * An empty string ("") input returns the empty string.
     *
     *
     * If the stripChars String is `null`, whitespace is
     * stripped as defined by [Character.isWhitespace].
     *
     * <pre>
     * StringUtils.stripEnd(null, *)          = null
     * StringUtils.stripEnd("", *)            = ""
     * StringUtils.stripEnd("abc", "")        = "abc"
     * StringUtils.stripEnd("abc", null)      = "abc"
     * StringUtils.stripEnd("  abc", null)    = "  abc"
     * StringUtils.stripEnd("abc  ", null)    = "abc"
     * StringUtils.stripEnd(" abc ", null)    = " abc"
     * StringUtils.stripEnd("  abcyx", "xyz") = "  abc"
     * StringUtils.stripEnd("120.00", ".0")   = "12"
    </pre> *
     *
     * @param str  the String to remove characters from, may be null
     * @param stripChars  the set of characters to remove, null treated as whitespace
     * @return the stripped String, `null` if null String input
     */
    fun stripEnd(str: String, stripChars: String?): String {
        var end: Int
        if (str == null || str.length.also { end = it } == 0) {
            return str
        }
        if (stripChars == null) {
            while (end != 0 && Character.isWhitespace(str[end - 1])) {
                end--
            }
        } else if (stripChars.isEmpty()) {
            return str
        } else {
            while (end != 0 && stripChars.indexOf(str[end - 1]) != INDEX_NOT_FOUND) {
                end--
            }
        }
        return str.substring(0, end)
    }
    // StripAll
//-----------------------------------------------------------------------
    /**
     *
     * Strips whitespace from the start and end of every String in an array.
     * Whitespace is defined by [Character.isWhitespace].
     *
     *
     * A new array is returned each time, except for length zero.
     * A `null` array will return `null`.
     * An empty array will return itself.
     * A `null` array entry will be ignored.
     *
     * <pre>
     * StringUtils.stripAll(null)             = null
     * StringUtils.stripAll([])               = []
     * StringUtils.stripAll(["abc", "  abc"]) = ["abc", "abc"]
     * StringUtils.stripAll(["abc  ", null])  = ["abc", null]
    </pre> *
     *
     * @param strs  the array to remove whitespace from, may be null
     * @return the stripped Strings, `null` if null array input
     */
    fun stripAll(vararg strs: String?): Array<String> {
        return stripAll(strs, null)
    }

    /**
     *
     * Strips any of a set of characters from the start and end of every
     * String in an array.
     *
     * Whitespace is defined by [Character.isWhitespace].
     *
     *
     * A new array is returned each time, except for length zero.
     * A `null` array will return `null`.
     * An empty array will return itself.
     * A `null` array entry will be ignored.
     * A `null` stripChars will strip whitespace as defined by
     * [Character.isWhitespace].
     *
     * <pre>
     * StringUtils.stripAll(null, *)                = null
     * StringUtils.stripAll([], *)                  = []
     * StringUtils.stripAll(["abc", "  abc"], null) = ["abc", "abc"]
     * StringUtils.stripAll(["abc  ", null], null)  = ["abc", null]
     * StringUtils.stripAll(["abc  ", null], "yz")  = ["abc  ", null]
     * StringUtils.stripAll(["yabcz", null], "yz")  = ["abc", null]
    </pre> *
     *
     * @param strs  the array to remove characters from, may be null
     * @param stripChars  the characters to remove, null treated as whitespace
     * @return the stripped Strings, `null` if null array input
     */
    fun stripAll(strs: Array<String?>?, stripChars: String?): Array<String?>? {
        var strsLen: Int
        if (strs == null || strs.size.also { strsLen = it } == 0) {
            return strs
        }
        val newArr = arrayOfNulls<String>(strsLen)
        for (i in 0 until strsLen) {
            newArr[i] = strip(strs[i]!!, stripChars)
        }
        return newArr
    }

    /**
     *
     * Removes diacritics (~= accents) from a string. The case will not be altered.
     *
     * For instance, '' will be replaced by 'a'.
     *
     * Note that ligatures will be left as is.
     *
     * <pre>
     * StringUtils.stripAccents(null)                = null
     * StringUtils.stripAccents("")                  = ""
     * StringUtils.stripAccents("control")           = "control"
     * StringUtils.stripAccents("clair")     = "eclair"
    </pre> *
     *
     * @param input String to be stripped
     * @return input text with diacritics removed
     *
     * @since 3.0
     */
// See also Lucene's ASCIIFoldingFilter (Lucene 2.9) that replaces accented characters by their unaccented equivalent (and uncommitted bug fix: https://issues.apache.org/jira/browse/LUCENE-1343?focusedCommentId=12858907&page=com.atlassian.jira.plugin.system.issuetabpanels%3Acomment-tabpanel#action_12858907).
    fun stripAccents(input: String?): String? {
        if (input == null) {
            return null
        }
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+") //$NON-NLS-1$
        val decomposed = StringBuilder(Normalizer.normalize(input, Normalizer.Form.NFD))
        convertRemainingAccentCharacters(decomposed)
        // Note that this doesn't correctly remove ligatures...
        return pattern.matcher(decomposed).replaceAll(EMPTY)
    }

    private fun convertRemainingAccentCharacters(decomposed: StringBuilder) {
        for (i in 0 until decomposed.length) {
            if (decomposed[i] == '\u0141') {
                decomposed.deleteCharAt(i)
                decomposed.insert(i, 'L')
            } else if (decomposed[i] == '\u0142') {
                decomposed.deleteCharAt(i)
                decomposed.insert(i, 'l')
            }
        }
    }
    // Equals
//-----------------------------------------------------------------------
    /**
     *
     * Compares two CharSequences, returning `true` if they represent
     * equal sequences of characters.
     *
     *
     * `null`s are handled without exceptions. Two `null`
     * references are considered to be equal. The comparison is **case sensitive**.
     *
     * <pre>
     * StringUtils.equals(null, null)   = true
     * StringUtils.equals(null, "abc")  = false
     * StringUtils.equals("abc", null)  = false
     * StringUtils.equals("abc", "abc") = true
     * StringUtils.equals("abc", "ABC") = false
    </pre> *
     *
     * @param cs1  the first CharSequence, may be `null`
     * @param cs2  the second CharSequence, may be `null`
     * @return `true` if the CharSequences are equal (case-sensitive), or both `null`
     * @since 3.0 Changed signature from equals(String, String) to equals(CharSequence, CharSequence)
     * @see Object.equals
     * @see .equalsIgnoreCase
     */
    fun equals(cs1: CharSequence?, cs2: CharSequence?): Boolean {
        if (cs1 === cs2) {
            return true
        }
        if (cs1 == null || cs2 == null) {
            return false
        }
        if (cs1.length != cs2.length) {
            return false
        }
        if (cs1 is String && cs2 is String) {
            return cs1 == cs2
        }
        // Step-wise comparison
        val length = cs1.length
        for (i in 0 until length) {
            if (cs1[i] != cs2[i]) {
                return false
            }
        }
        return true
    }

    /**
     *
     * Compares two CharSequences, returning `true` if they represent
     * equal sequences of characters, ignoring case.
     *
     *
     * `null`s are handled without exceptions. Two `null`
     * references are considered equal. The comparison is **case insensitive**.
     *
     * <pre>
     * StringUtils.equalsIgnoreCase(null, null)   = true
     * StringUtils.equalsIgnoreCase(null, "abc")  = false
     * StringUtils.equalsIgnoreCase("abc", null)  = false
     * StringUtils.equalsIgnoreCase("abc", "abc") = true
     * StringUtils.equalsIgnoreCase("abc", "ABC") = true
    </pre> *
     *
     * @param cs1  the first CharSequence, may be `null`
     * @param cs2  the second CharSequence, may be `null`
     * @return `true` if the CharSequences are equal (case-insensitive), or both `null`
     * @since 3.0 Changed signature from equalsIgnoreCase(String, String) to equalsIgnoreCase(CharSequence, CharSequence)
     * @see .equals
     */
    fun equalsIgnoreCase(cs1: CharSequence?, cs2: CharSequence?): Boolean {
        if (cs1 === cs2) {
            return true
        }
        if (cs1 == null || cs2 == null) {
            return false
        }
        return if (cs1.length != cs2.length) {
            false
        } else CharSequenceUtils.regionMatches(cs1, true, 0, cs2, 0, cs1.length)
    }
    /**
     *
     * Compare two Strings lexicographically, as per [String.compareTo], returning :
     *
     *  * `int = 0`, if `str1` is equal to `str2` (or both `null`)
     *  * `int < 0`, if `str1` is less than `str2`
     *  * `int > 0`, if `str1` is greater than `str2`
     *
     *
     *
     * This is a `null` safe version of :
     * <blockquote><pre>str1.compareTo(str2)</pre></blockquote>
     *
     *
     * `null` inputs are handled according to the `nullIsLess` parameter.
     * Two `null` references are considered equal.
     *
     * <pre>
     * StringUtils.compare(null, null, *)     = 0
     * StringUtils.compare(null , "a", true)  &lt; 0
     * StringUtils.compare(null , "a", false) &gt; 0
     * StringUtils.compare("a", null, true)   &gt; 0
     * StringUtils.compare("a", null, false)  &lt; 0
     * StringUtils.compare("abc", "abc", *)   = 0
     * StringUtils.compare("a", "b", *)       &lt; 0
     * StringUtils.compare("b", "a", *)       &gt; 0
     * StringUtils.compare("a", "B", *)       &gt; 0
     * StringUtils.compare("ab", "abc", *)    &lt; 0
    </pre> *
     *
     * @see String.compareTo
     * @param str1  the String to compare from
     * @param str2  the String to compare to
     * @param nullIsLess  whether consider `null` value less than non-`null` value
     * @return &lt; 0, 0, &gt; 0, if `str1` is respectively less, equal ou greater than `str2`
     * @since 3.5
     */
// Compare
//-----------------------------------------------------------------------
    /**
     *
     * Compare two Strings lexicographically, as per [String.compareTo], returning :
     *
     *  * `int = 0`, if `str1` is equal to `str2` (or both `null`)
     *  * `int < 0`, if `str1` is less than `str2`
     *  * `int > 0`, if `str1` is greater than `str2`
     *
     *
     *
     * This is a `null` safe version of :
     * <blockquote><pre>str1.compareTo(str2)</pre></blockquote>
     *
     *
     * `null` value is considered less than non-`null` value.
     * Two `null` references are considered equal.
     *
     * <pre>
     * StringUtils.compare(null, null)   = 0
     * StringUtils.compare(null , "a")   &lt; 0
     * StringUtils.compare("a", null)    &gt; 0
     * StringUtils.compare("abc", "abc") = 0
     * StringUtils.compare("a", "b")     &lt; 0
     * StringUtils.compare("b", "a")     &gt; 0
     * StringUtils.compare("a", "B")     &gt; 0
     * StringUtils.compare("ab", "abc")  &lt; 0
    </pre> *
     *
     * @see .compare
     * @see String.compareTo
     * @param str1  the String to compare from
     * @param str2  the String to compare to
     * @return &lt; 0, 0, &gt; 0, if `str1` is respectively less, equal or greater than `str2`
     * @since 3.5
     */
    @JvmOverloads
    fun compare(str1: String?, str2: String?, nullIsLess: Boolean = true): Int {
        if (str1 === str2) {
            return 0
        }
        if (str1 == null) {
            return if (nullIsLess) -1 else 1
        }
        return if (str2 == null) {
            if (nullIsLess) 1 else -1
        } else str1.compareTo(str2)
    }
    /**
     *
     * Compare two Strings lexicographically, ignoring case differences,
     * as per [String.compareToIgnoreCase], returning :
     *
     *  * `int = 0`, if `str1` is equal to `str2` (or both `null`)
     *  * `int < 0`, if `str1` is less than `str2`
     *  * `int > 0`, if `str1` is greater than `str2`
     *
     *
     *
     * This is a `null` safe version of :
     * <blockquote><pre>str1.compareToIgnoreCase(str2)</pre></blockquote>
     *
     *
     * `null` inputs are handled according to the `nullIsLess` parameter.
     * Two `null` references are considered equal.
     * Comparison is case insensitive.
     *
     * <pre>
     * StringUtils.compareIgnoreCase(null, null, *)     = 0
     * StringUtils.compareIgnoreCase(null , "a", true)  &lt; 0
     * StringUtils.compareIgnoreCase(null , "a", false) &gt; 0
     * StringUtils.compareIgnoreCase("a", null, true)   &gt; 0
     * StringUtils.compareIgnoreCase("a", null, false)  &lt; 0
     * StringUtils.compareIgnoreCase("abc", "abc", *)   = 0
     * StringUtils.compareIgnoreCase("abc", "ABC", *)   = 0
     * StringUtils.compareIgnoreCase("a", "b", *)       &lt; 0
     * StringUtils.compareIgnoreCase("b", "a", *)       &gt; 0
     * StringUtils.compareIgnoreCase("a", "B", *)       &lt; 0
     * StringUtils.compareIgnoreCase("A", "b", *)       &lt; 0
     * StringUtils.compareIgnoreCase("ab", "abc", *)    &lt; 0
    </pre> *
     *
     * @see String.compareToIgnoreCase
     * @param str1  the String to compare from
     * @param str2  the String to compare to
     * @param nullIsLess  whether consider `null` value less than non-`null` value
     * @return &lt; 0, 0, &gt; 0, if `str1` is respectively less, equal ou greater than `str2`,
     * ignoring case differences.
     * @since 3.5
     */
    /**
     *
     * Compare two Strings lexicographically, ignoring case differences,
     * as per [String.compareToIgnoreCase], returning :
     *
     *  * `int = 0`, if `str1` is equal to `str2` (or both `null`)
     *  * `int < 0`, if `str1` is less than `str2`
     *  * `int > 0`, if `str1` is greater than `str2`
     *
     *
     *
     * This is a `null` safe version of :
     * <blockquote><pre>str1.compareToIgnoreCase(str2)</pre></blockquote>
     *
     *
     * `null` value is considered less than non-`null` value.
     * Two `null` references are considered equal.
     * Comparison is case insensitive.
     *
     * <pre>
     * StringUtils.compareIgnoreCase(null, null)   = 0
     * StringUtils.compareIgnoreCase(null , "a")   &lt; 0
     * StringUtils.compareIgnoreCase("a", null)    &gt; 0
     * StringUtils.compareIgnoreCase("abc", "abc") = 0
     * StringUtils.compareIgnoreCase("abc", "ABC") = 0
     * StringUtils.compareIgnoreCase("a", "b")     &lt; 0
     * StringUtils.compareIgnoreCase("b", "a")     &gt; 0
     * StringUtils.compareIgnoreCase("a", "B")     &lt; 0
     * StringUtils.compareIgnoreCase("A", "b")     &lt; 0
     * StringUtils.compareIgnoreCase("ab", "ABC")  &lt; 0
    </pre> *
     *
     * @see .compareIgnoreCase
     * @see String.compareToIgnoreCase
     * @param str1  the String to compare from
     * @param str2  the String to compare to
     * @return &lt; 0, 0, &gt; 0, if `str1` is respectively less, equal ou greater than `str2`,
     * ignoring case differences.
     * @since 3.5
     */
    @JvmOverloads
    fun compareIgnoreCase(str1: String?, str2: String?, nullIsLess: Boolean = true): Int {
        if (str1 === str2) {
            return 0
        }
        if (str1 == null) {
            return if (nullIsLess) -1 else 1
        }
        return if (str2 == null) {
            if (nullIsLess) 1 else -1
        } else str1.compareTo(str2, ignoreCase = true)
    }

    /**
     *
     * Compares given `string` to a CharSequences vararg of `searchStrings`,
     * returning `true` if the `string` is equal to any of the `searchStrings`.
     *
     * <pre>
     * StringUtils.equalsAny(null, (CharSequence[]) null) = false
     * StringUtils.equalsAny(null, null, null)    = true
     * StringUtils.equalsAny(null, "abc", "def")  = false
     * StringUtils.equalsAny("abc", null, "def")  = false
     * StringUtils.equalsAny("abc", "abc", "def") = true
     * StringUtils.equalsAny("abc", "ABC", "DEF") = false
    </pre> *
     *
     * @param string to compare, may be `null`.
     * @param searchStrings a vararg of strings, may be `null`.
     * @return `true` if the string is equal (case-sensitive) to any other element of `searchStrings`;
     * `false` if `searchStrings` is null or contains no matches.
     * @since 3.5
     */
    fun equalsAny(string: CharSequence?, vararg searchStrings: CharSequence): Boolean {
        if (ArrayUtils.isNotEmpty(searchStrings)) {
            for (next in searchStrings) {
                if (equals(string, next)) {
                    return true
                }
            }
        }
        return false
    }

    /**
     *
     * Compares given `string` to a CharSequences vararg of `searchStrings`,
     * returning `true` if the `string` is equal to any of the `searchStrings`, ignoring case.
     *
     * <pre>
     * StringUtils.equalsAnyIgnoreCase(null, (CharSequence[]) null) = false
     * StringUtils.equalsAnyIgnoreCase(null, null, null)    = true
     * StringUtils.equalsAnyIgnoreCase(null, "abc", "def")  = false
     * StringUtils.equalsAnyIgnoreCase("abc", null, "def")  = false
     * StringUtils.equalsAnyIgnoreCase("abc", "abc", "def") = true
     * StringUtils.equalsAnyIgnoreCase("abc", "ABC", "DEF") = true
    </pre> *
     *
     * @param string to compare, may be `null`.
     * @param searchStrings a vararg of strings, may be `null`.
     * @return `true` if the string is equal (case-insensitive) to any other element of `searchStrings`;
     * `false` if `searchStrings` is null or contains no matches.
     * @since 3.5
     */
    fun equalsAnyIgnoreCase(string: CharSequence?, vararg searchStrings: CharSequence): Boolean {
        if (ArrayUtils.isNotEmpty(searchStrings)) {
            for (next in searchStrings) {
                if (equalsIgnoreCase(string, next)) {
                    return true
                }
            }
        }
        return false
    }
    // IndexOf
//-----------------------------------------------------------------------
    /**
     * Returns the index within `seq` of the first occurrence of
     * the specified character. If a character with value
     * `searchChar` occurs in the character sequence represented by
     * `seq` `CharSequence` object, then the index (in Unicode
     * code units) of the first such occurrence is returned. For
     * values of `searchChar` in the range from 0 to 0xFFFF
     * (inclusive), this is the smallest value *k* such that:
     * <blockquote><pre>
     * this.charAt(*k*) == searchChar
    </pre></blockquote> *
     * is true. For other values of `searchChar`, it is the
     * smallest value *k* such that:
     * <blockquote><pre>
     * this.codePointAt(*k*) == searchChar
    </pre></blockquote> *
     * is true. In either case, if no such character occurs in `seq`,
     * then `INDEX_NOT_FOUND (-1)` is returned.
     *
     *
     * Furthermore, a `null` or empty ("") CharSequence will
     * return `INDEX_NOT_FOUND (-1)`.
     *
     * <pre>
     * StringUtils.indexOf(null, *)         = -1
     * StringUtils.indexOf("", *)           = -1
     * StringUtils.indexOf("aabaabaa", 'a') = 0
     * StringUtils.indexOf("aabaabaa", 'b') = 2
    </pre> *
     *
     * @param seq  the CharSequence to check, may be null
     * @param searchChar  the character to find
     * @return the first index of the search character,
     * -1 if no match or `null` string input
     * @since 2.0
     * @since 3.0 Changed signature from indexOf(String, int) to indexOf(CharSequence, int)
     * @since 3.6 Updated [CharSequenceUtils] call to behave more like `String`
     */
    fun indexOf(seq: CharSequence?, searchChar: Int): Int {
        return if (isEmpty(seq)) {
            INDEX_NOT_FOUND
        } else CharSequenceUtils.indexOf(seq, searchChar, 0)
    }

    /**
     *
     * Returns the index within `seq` of the first occurrence of the
     * specified character, starting the search at the specified index.
     *
     *
     * If a character with value `searchChar` occurs in the
     * character sequence represented by the `seq` `CharSequence`
     * object at an index no smaller than `startPos`, then
     * the index of the first such occurrence is returned. For values
     * of `searchChar` in the range from 0 to 0xFFFF (inclusive),
     * this is the smallest value *k* such that:
     * <blockquote><pre>
     * (this.charAt(*k*) == searchChar) &amp;&amp; (*k* &gt;= startPos)
    </pre></blockquote> *
     * is true. For other values of `searchChar`, it is the
     * smallest value *k* such that:
     * <blockquote><pre>
     * (this.codePointAt(*k*) == searchChar) &amp;&amp; (*k* &gt;= startPos)
    </pre></blockquote> *
     * is true. In either case, if no such character occurs in `seq`
     * at or after position `startPos`, then
     * `-1` is returned.
     *
     *
     *
     * There is no restriction on the value of `startPos`. If it
     * is negative, it has the same effect as if it were zero: this entire
     * string may be searched. If it is greater than the length of this
     * string, it has the same effect as if it were equal to the length of
     * this string: `(INDEX_NOT_FOUND) -1` is returned. Furthermore, a
     * `null` or empty ("") CharSequence will
     * return `(INDEX_NOT_FOUND) -1`.
     *
     *
     * All indices are specified in `char` values
     * (Unicode code units).
     *
     * <pre>
     * StringUtils.indexOf(null, *, *)          = -1
     * StringUtils.indexOf("", *, *)            = -1
     * StringUtils.indexOf("aabaabaa", 'b', 0)  = 2
     * StringUtils.indexOf("aabaabaa", 'b', 3)  = 5
     * StringUtils.indexOf("aabaabaa", 'b', 9)  = -1
     * StringUtils.indexOf("aabaabaa", 'b', -1) = 2
    </pre> *
     *
     * @param seq  the CharSequence to check, may be null
     * @param searchChar  the character to find
     * @param startPos  the start position, negative treated as zero
     * @return the first index of the search character (always  startPos),
     * -1 if no match or `null` string input
     * @since 2.0
     * @since 3.0 Changed signature from indexOf(String, int, int) to indexOf(CharSequence, int, int)
     * @since 3.6 Updated [CharSequenceUtils] call to behave more like `String`
     */
    fun indexOf(seq: CharSequence?, searchChar: Int, startPos: Int): Int {
        return if (isEmpty(seq)) {
            INDEX_NOT_FOUND
        } else CharSequenceUtils.indexOf(seq, searchChar, startPos)
    }

    /**
     *
     * Finds the first index within a CharSequence, handling `null`.
     * This method uses [String.indexOf] if possible.
     *
     *
     * A `null` CharSequence will return `-1`.
     *
     * <pre>
     * StringUtils.indexOf(null, *)          = -1
     * StringUtils.indexOf(*, null)          = -1
     * StringUtils.indexOf("", "")           = 0
     * StringUtils.indexOf("", *)            = -1 (except when * = "")
     * StringUtils.indexOf("aabaabaa", "a")  = 0
     * StringUtils.indexOf("aabaabaa", "b")  = 2
     * StringUtils.indexOf("aabaabaa", "ab") = 1
     * StringUtils.indexOf("aabaabaa", "")   = 0
    </pre> *
     *
     * @param seq  the CharSequence to check, may be null
     * @param searchSeq  the CharSequence to find, may be null
     * @return the first index of the search CharSequence,
     * -1 if no match or `null` string input
     * @since 2.0
     * @since 3.0 Changed signature from indexOf(String, String) to indexOf(CharSequence, CharSequence)
     */
    fun indexOf(seq: CharSequence?, searchSeq: CharSequence?): Int {
        return if (seq == null || searchSeq == null) {
            INDEX_NOT_FOUND
        } else CharSequenceUtils.indexOf(seq, searchSeq, 0)
    }

    /**
     *
     * Finds the first index within a CharSequence, handling `null`.
     * This method uses [String.indexOf] if possible.
     *
     *
     * A `null` CharSequence will return `-1`.
     * A negative start position is treated as zero.
     * An empty ("") search CharSequence always matches.
     * A start position greater than the string length only matches
     * an empty search CharSequence.
     *
     * <pre>
     * StringUtils.indexOf(null, *, *)          = -1
     * StringUtils.indexOf(*, null, *)          = -1
     * StringUtils.indexOf("", "", 0)           = 0
     * StringUtils.indexOf("", *, 0)            = -1 (except when * = "")
     * StringUtils.indexOf("aabaabaa", "a", 0)  = 0
     * StringUtils.indexOf("aabaabaa", "b", 0)  = 2
     * StringUtils.indexOf("aabaabaa", "ab", 0) = 1
     * StringUtils.indexOf("aabaabaa", "b", 3)  = 5
     * StringUtils.indexOf("aabaabaa", "b", 9)  = -1
     * StringUtils.indexOf("aabaabaa", "b", -1) = 2
     * StringUtils.indexOf("aabaabaa", "", 2)   = 2
     * StringUtils.indexOf("abc", "", 9)        = 3
    </pre> *
     *
     * @param seq  the CharSequence to check, may be null
     * @param searchSeq  the CharSequence to find, may be null
     * @param startPos  the start position, negative treated as zero
     * @return the first index of the search CharSequence (always  startPos),
     * -1 if no match or `null` string input
     * @since 2.0
     * @since 3.0 Changed signature from indexOf(String, String, int) to indexOf(CharSequence, CharSequence, int)
     */
    fun indexOf(seq: CharSequence?, searchSeq: CharSequence?, startPos: Int): Int {
        return if (seq == null || searchSeq == null) {
            INDEX_NOT_FOUND
        } else CharSequenceUtils.indexOf(seq, searchSeq, startPos)
    }

    /**
     *
     * Finds the n-th index within a CharSequence, handling `null`.
     * This method uses [String.indexOf] if possible.
     *
     * **Note:** The code starts looking for a match at the start of the target,
     * incrementing the starting index by one after each successful match
     * (unless `searchStr` is an empty string in which case the position
     * is never incremented and `0` is returned immediately).
     * This means that matches may overlap.
     *
     * A `null` CharSequence will return `-1`.
     *
     * <pre>
     * StringUtils.ordinalIndexOf(null, *, *)          = -1
     * StringUtils.ordinalIndexOf(*, null, *)          = -1
     * StringUtils.ordinalIndexOf("", "", *)           = 0
     * StringUtils.ordinalIndexOf("aabaabaa", "a", 1)  = 0
     * StringUtils.ordinalIndexOf("aabaabaa", "a", 2)  = 1
     * StringUtils.ordinalIndexOf("aabaabaa", "b", 1)  = 2
     * StringUtils.ordinalIndexOf("aabaabaa", "b", 2)  = 5
     * StringUtils.ordinalIndexOf("aabaabaa", "ab", 1) = 1
     * StringUtils.ordinalIndexOf("aabaabaa", "ab", 2) = 4
     * StringUtils.ordinalIndexOf("aabaabaa", "", 1)   = 0
     * StringUtils.ordinalIndexOf("aabaabaa", "", 2)   = 0
    </pre> *
     *
     *
     * Matches may overlap:
     * <pre>
     * StringUtils.ordinalIndexOf("ababab", "aba", 1)   = 0
     * StringUtils.ordinalIndexOf("ababab", "aba", 2)   = 2
     * StringUtils.ordinalIndexOf("ababab", "aba", 3)   = -1
     *
     * StringUtils.ordinalIndexOf("abababab", "abab", 1) = 0
     * StringUtils.ordinalIndexOf("abababab", "abab", 2) = 2
     * StringUtils.ordinalIndexOf("abababab", "abab", 3) = 4
     * StringUtils.ordinalIndexOf("abababab", "abab", 4) = -1
    </pre> *
     *
     *
     * Note that 'head(CharSequence str, int n)' may be implemented as:
     *
     * <pre>
     * str.substring(0, lastOrdinalIndexOf(str, "\n", n))
    </pre> *
     *
     * @param str  the CharSequence to check, may be null
     * @param searchStr  the CharSequence to find, may be null
     * @param ordinal  the n-th `searchStr` to find
     * @return the n-th index of the search CharSequence,
     * `-1` (`INDEX_NOT_FOUND`) if no match or `null` string input
     * @since 2.1
     * @since 3.0 Changed signature from ordinalIndexOf(String, String, int) to ordinalIndexOf(CharSequence, CharSequence, int)
     */
    fun ordinalIndexOf(str: CharSequence?, searchStr: CharSequence?, ordinal: Int): Int {
        return ordinalIndexOf(str, searchStr, ordinal, false)
    }

    /**
     *
     * Finds the n-th index within a String, handling `null`.
     * This method uses [String.indexOf] if possible.
     *
     * Note that matches may overlap
     *
     *
     *
     *
     * A `null` CharSequence will return `-1`.
     *
     * @param str  the CharSequence to check, may be null
     * @param searchStr  the CharSequence to find, may be null
     * @param ordinal  the n-th `searchStr` to find, overlapping matches are allowed.
     * @param lastIndex true if lastOrdinalIndexOf() otherwise false if ordinalIndexOf()
     * @return the n-th index of the search CharSequence,
     * `-1` (`INDEX_NOT_FOUND`) if no match or `null` string input
     */
// Shared code between ordinalIndexOf(String, String, int) and lastOrdinalIndexOf(String, String, int)
    private fun ordinalIndexOf(str: CharSequence?, searchStr: CharSequence?, ordinal: Int, lastIndex: Boolean): Int {
        if (str == null || searchStr == null || ordinal <= 0) {
            return INDEX_NOT_FOUND
        }
        if (searchStr.length == 0) {
            return if (lastIndex) str.length else 0
        }
        var found = 0
        // set the initial index beyond the end of the string
// this is to allow for the initial index decrement/increment
        var index = if (lastIndex) str.length else INDEX_NOT_FOUND
        do {
            index = if (lastIndex) {
                CharSequenceUtils.lastIndexOf(str, searchStr, index - 1) // step backwards thru string
            } else {
                CharSequenceUtils.indexOf(str, searchStr, index + 1) // step forwards through string
            }
            if (index < 0) {
                return index
            }
            found++
        } while (found < ordinal)
        return index
    }
    /**
     *
     * Case in-sensitive find of the first index within a CharSequence
     * from the specified position.
     *
     *
     * A `null` CharSequence will return `-1`.
     * A negative start position is treated as zero.
     * An empty ("") search CharSequence always matches.
     * A start position greater than the string length only matches
     * an empty search CharSequence.
     *
     * <pre>
     * StringUtils.indexOfIgnoreCase(null, *, *)          = -1
     * StringUtils.indexOfIgnoreCase(*, null, *)          = -1
     * StringUtils.indexOfIgnoreCase("", "", 0)           = 0
     * StringUtils.indexOfIgnoreCase("aabaabaa", "A", 0)  = 0
     * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 0)  = 2
     * StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 0) = 1
     * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 3)  = 5
     * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 9)  = -1
     * StringUtils.indexOfIgnoreCase("aabaabaa", "B", -1) = 2
     * StringUtils.indexOfIgnoreCase("aabaabaa", "", 2)   = 2
     * StringUtils.indexOfIgnoreCase("abc", "", 9)        = -1
    </pre> *
     *
     * @param str  the CharSequence to check, may be null
     * @param searchStr  the CharSequence to find, may be null
     * @param startPos  the start position, negative treated as zero
     * @return the first index of the search CharSequence (always  startPos),
     * -1 if no match or `null` string input
     * @since 2.5
     * @since 3.0 Changed signature from indexOfIgnoreCase(String, String, int) to indexOfIgnoreCase(CharSequence, CharSequence, int)
     */
    /**
     *
     * Case in-sensitive find of the first index within a CharSequence.
     *
     *
     * A `null` CharSequence will return `-1`.
     * A negative start position is treated as zero.
     * An empty ("") search CharSequence always matches.
     * A start position greater than the string length only matches
     * an empty search CharSequence.
     *
     * <pre>
     * StringUtils.indexOfIgnoreCase(null, *)          = -1
     * StringUtils.indexOfIgnoreCase(*, null)          = -1
     * StringUtils.indexOfIgnoreCase("", "")           = 0
     * StringUtils.indexOfIgnoreCase("aabaabaa", "a")  = 0
     * StringUtils.indexOfIgnoreCase("aabaabaa", "b")  = 2
     * StringUtils.indexOfIgnoreCase("aabaabaa", "ab") = 1
    </pre> *
     *
     * @param str  the CharSequence to check, may be null
     * @param searchStr  the CharSequence to find, may be null
     * @return the first index of the search CharSequence,
     * -1 if no match or `null` string input
     * @since 2.5
     * @since 3.0 Changed signature from indexOfIgnoreCase(String, String) to indexOfIgnoreCase(CharSequence, CharSequence)
     */
    @JvmOverloads
    fun indexOfIgnoreCase(str: CharSequence?, searchStr: CharSequence?, startPos: Int = 0): Int {
        var startPos = startPos
        if (str == null || searchStr == null) {
            return INDEX_NOT_FOUND
        }
        if (startPos < 0) {
            startPos = 0
        }
        val endLimit = str.length - searchStr.length + 1
        if (startPos > endLimit) {
            return INDEX_NOT_FOUND
        }
        if (searchStr.length == 0) {
            return startPos
        }
        for (i in startPos until endLimit) {
            if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, searchStr.length)) {
                return i
            }
        }
        return INDEX_NOT_FOUND
    }
    // LastIndexOf
//-----------------------------------------------------------------------
    /**
     * Returns the index within `seq` of the last occurrence of
     * the specified character. For values of `searchChar` in the
     * range from 0 to 0xFFFF (inclusive), the index (in Unicode code
     * units) returned is the largest value *k* such that:
     * <blockquote><pre>
     * this.charAt(*k*) == searchChar
    </pre></blockquote> *
     * is true. For other values of `searchChar`, it is the
     * largest value *k* such that:
     * <blockquote><pre>
     * this.codePointAt(*k*) == searchChar
    </pre></blockquote> *
     * is true.  In either case, if no such character occurs in this
     * string, then `-1` is returned. Furthermore, a `null` or empty ("")
     * `CharSequence` will return `-1`. The
     * `seq` `CharSequence` object is searched backwards
     * starting at the last character.
     *
     * <pre>
     * StringUtils.lastIndexOf(null, *)         = -1
     * StringUtils.lastIndexOf("", *)           = -1
     * StringUtils.lastIndexOf("aabaabaa", 'a') = 7
     * StringUtils.lastIndexOf("aabaabaa", 'b') = 5
    </pre> *
     *
     * @param seq  the `CharSequence` to check, may be null
     * @param searchChar  the character to find
     * @return the last index of the search character,
     * -1 if no match or `null` string input
     * @since 2.0
     * @since 3.0 Changed signature from lastIndexOf(String, int) to lastIndexOf(CharSequence, int)
     * @since 3.6 Updated [CharSequenceUtils] call to behave more like `String`
     */
    fun lastIndexOf(seq: CharSequence, searchChar: Int): Int {
        return if (isEmpty(seq)) {
            INDEX_NOT_FOUND
        } else CharSequenceUtils.lastIndexOf(seq, searchChar, seq.length)
    }

    /**
     * Returns the index within `seq` of the last occurrence of
     * the specified character, searching backward starting at the
     * specified index. For values of `searchChar` in the range
     * from 0 to 0xFFFF (inclusive), the index returned is the largest
     * value *k* such that:
     * <blockquote><pre>
     * (this.charAt(*k*) == searchChar) &amp;&amp; (*k* &lt;= startPos)
    </pre></blockquote> *
     * is true. For other values of `searchChar`, it is the
     * largest value *k* such that:
     * <blockquote><pre>
     * (this.codePointAt(*k*) == searchChar) &amp;&amp; (*k* &lt;= startPos)
    </pre></blockquote> *
     * is true. In either case, if no such character occurs in `seq`
     * at or before position `startPos`, then
     * `-1` is returned. Furthermore, a `null` or empty ("")
     * `CharSequence` will return `-1`. A start position greater
     * than the string length searches the whole string.
     * The search starts at the `startPos` and works backwards;
     * matches starting after the start position are ignored.
     *
     *
     * All indices are specified in `char` values
     * (Unicode code units).
     *
     * <pre>
     * StringUtils.lastIndexOf(null, *, *)          = -1
     * StringUtils.lastIndexOf("", *,  *)           = -1
     * StringUtils.lastIndexOf("aabaabaa", 'b', 8)  = 5
     * StringUtils.lastIndexOf("aabaabaa", 'b', 4)  = 2
     * StringUtils.lastIndexOf("aabaabaa", 'b', 0)  = -1
     * StringUtils.lastIndexOf("aabaabaa", 'b', 9)  = 5
     * StringUtils.lastIndexOf("aabaabaa", 'b', -1) = -1
     * StringUtils.lastIndexOf("aabaabaa", 'a', 0)  = 0
    </pre> *
     *
     * @param seq  the CharSequence to check, may be null
     * @param searchChar  the character to find
     * @param startPos  the start position
     * @return the last index of the search character (always  startPos),
     * -1 if no match or `null` string input
     * @since 2.0
     * @since 3.0 Changed signature from lastIndexOf(String, int, int) to lastIndexOf(CharSequence, int, int)
     */
    fun lastIndexOf(seq: CharSequence?, searchChar: Int, startPos: Int): Int {
        return if (isEmpty(seq)) {
            INDEX_NOT_FOUND
        } else CharSequenceUtils.lastIndexOf(seq, searchChar, startPos)
    }

    /**
     *
     * Finds the last index within a CharSequence, handling `null`.
     * This method uses [String.lastIndexOf] if possible.
     *
     *
     * A `null` CharSequence will return `-1`.
     *
     * <pre>
     * StringUtils.lastIndexOf(null, *)          = -1
     * StringUtils.lastIndexOf(*, null)          = -1
     * StringUtils.lastIndexOf("", "")           = 0
     * StringUtils.lastIndexOf("aabaabaa", "a")  = 7
     * StringUtils.lastIndexOf("aabaabaa", "b")  = 5
     * StringUtils.lastIndexOf("aabaabaa", "ab") = 4
     * StringUtils.lastIndexOf("aabaabaa", "")   = 8
    </pre> *
     *
     * @param seq  the CharSequence to check, may be null
     * @param searchSeq  the CharSequence to find, may be null
     * @return the last index of the search String,
     * -1 if no match or `null` string input
     * @since 2.0
     * @since 3.0 Changed signature from lastIndexOf(String, String) to lastIndexOf(CharSequence, CharSequence)
     */
    fun lastIndexOf(seq: CharSequence?, searchSeq: CharSequence?): Int {
        return if (seq == null || searchSeq == null) {
            INDEX_NOT_FOUND
        } else CharSequenceUtils.lastIndexOf(seq, searchSeq, seq.length)
    }

    /**
     *
     * Finds the n-th last index within a String, handling `null`.
     * This method uses [String.lastIndexOf].
     *
     *
     * A `null` String will return `-1`.
     *
     * <pre>
     * StringUtils.lastOrdinalIndexOf(null, *, *)          = -1
     * StringUtils.lastOrdinalIndexOf(*, null, *)          = -1
     * StringUtils.lastOrdinalIndexOf("", "", *)           = 0
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 1)  = 7
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 2)  = 6
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 1)  = 5
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 2)  = 2
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 1) = 4
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 2) = 1
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "", 1)   = 8
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "", 2)   = 8
    </pre> *
     *
     *
     * Note that 'tail(CharSequence str, int n)' may be implemented as:
     *
     * <pre>
     * str.substring(lastOrdinalIndexOf(str, "\n", n) + 1)
    </pre> *
     *
     * @param str  the CharSequence to check, may be null
     * @param searchStr  the CharSequence to find, may be null
     * @param ordinal  the n-th last `searchStr` to find
     * @return the n-th last index of the search CharSequence,
     * `-1` (`INDEX_NOT_FOUND`) if no match or `null` string input
     * @since 2.5
     * @since 3.0 Changed signature from lastOrdinalIndexOf(String, String, int) to lastOrdinalIndexOf(CharSequence, CharSequence, int)
     */
    fun lastOrdinalIndexOf(str: CharSequence?, searchStr: CharSequence?, ordinal: Int): Int {
        return ordinalIndexOf(str, searchStr, ordinal, true)
    }

    /**
     *
     * Finds the last index within a CharSequence, handling `null`.
     * This method uses [String.lastIndexOf] if possible.
     *
     *
     * A `null` CharSequence will return `-1`.
     * A negative start position returns `-1`.
     * An empty ("") search CharSequence always matches unless the start position is negative.
     * A start position greater than the string length searches the whole string.
     * The search starts at the startPos and works backwards; matches starting after the start
     * position are ignored.
     *
     *
     * <pre>
     * StringUtils.lastIndexOf(null, *, *)          = -1
     * StringUtils.lastIndexOf(*, null, *)          = -1
     * StringUtils.lastIndexOf("aabaabaa", "a", 8)  = 7
     * StringUtils.lastIndexOf("aabaabaa", "b", 8)  = 5
     * StringUtils.lastIndexOf("aabaabaa", "ab", 8) = 4
     * StringUtils.lastIndexOf("aabaabaa", "b", 9)  = 5
     * StringUtils.lastIndexOf("aabaabaa", "b", -1) = -1
     * StringUtils.lastIndexOf("aabaabaa", "a", 0)  = 0
     * StringUtils.lastIndexOf("aabaabaa", "b", 0)  = -1
     * StringUtils.lastIndexOf("aabaabaa", "b", 1)  = -1
     * StringUtils.lastIndexOf("aabaabaa", "b", 2)  = 2
     * StringUtils.lastIndexOf("aabaabaa", "ba", 2)  = 2
    </pre> *
     *
     * @param seq  the CharSequence to check, may be null
     * @param searchSeq  the CharSequence to find, may be null
     * @param startPos  the start position, negative treated as zero
     * @return the last index of the search CharSequence (always  startPos),
     * -1 if no match or `null` string input
     * @since 2.0
     * @since 3.0 Changed signature from lastIndexOf(String, String, int) to lastIndexOf(CharSequence, CharSequence, int)
     */
    fun lastIndexOf(seq: CharSequence?, searchSeq: CharSequence?, startPos: Int): Int {
        return if (seq == null || searchSeq == null) {
            INDEX_NOT_FOUND
        } else CharSequenceUtils.lastIndexOf(seq, searchSeq, startPos)
    }

    /**
     *
     * Case in-sensitive find of the last index within a CharSequence.
     *
     *
     * A `null` CharSequence will return `-1`.
     * A negative start position returns `-1`.
     * An empty ("") search CharSequence always matches unless the start position is negative.
     * A start position greater than the string length searches the whole string.
     *
     * <pre>
     * StringUtils.lastIndexOfIgnoreCase(null, *)          = -1
     * StringUtils.lastIndexOfIgnoreCase(*, null)          = -1
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A")  = 7
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B")  = 5
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB") = 4
    </pre> *
     *
     * @param str  the CharSequence to check, may be null
     * @param searchStr  the CharSequence to find, may be null
     * @return the first index of the search CharSequence,
     * -1 if no match or `null` string input
     * @since 2.5
     * @since 3.0 Changed signature from lastIndexOfIgnoreCase(String, String) to lastIndexOfIgnoreCase(CharSequence, CharSequence)
     */
    fun lastIndexOfIgnoreCase(str: CharSequence?, searchStr: CharSequence?): Int {
        return if (str == null || searchStr == null) {
            INDEX_NOT_FOUND
        } else lastIndexOfIgnoreCase(str, searchStr, str.length)
    }

    /**
     *
     * Case in-sensitive find of the last index within a CharSequence
     * from the specified position.
     *
     *
     * A `null` CharSequence will return `-1`.
     * A negative start position returns `-1`.
     * An empty ("") search CharSequence always matches unless the start position is negative.
     * A start position greater than the string length searches the whole string.
     * The search starts at the startPos and works backwards; matches starting after the start
     * position are ignored.
     *
     *
     * <pre>
     * StringUtils.lastIndexOfIgnoreCase(null, *, *)          = -1
     * StringUtils.lastIndexOfIgnoreCase(*, null, *)          = -1
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 8)  = 7
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 8)  = 5
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB", 8) = 4
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 9)  = 5
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", -1) = -1
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 0)  = 0
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 0)  = -1
    </pre> *
     *
     * @param str  the CharSequence to check, may be null
     * @param searchStr  the CharSequence to find, may be null
     * @param startPos  the start position
     * @return the last index of the search CharSequence (always  startPos),
     * -1 if no match or `null` input
     * @since 2.5
     * @since 3.0 Changed signature from lastIndexOfIgnoreCase(String, String, int) to lastIndexOfIgnoreCase(CharSequence, CharSequence, int)
     */
    fun lastIndexOfIgnoreCase(str: CharSequence?, searchStr: CharSequence?, startPos: Int): Int {
        var startPos = startPos
        if (str == null || searchStr == null) {
            return INDEX_NOT_FOUND
        }
        if (startPos > str.length - searchStr.length) {
            startPos = str.length - searchStr.length
        }
        if (startPos < 0) {
            return INDEX_NOT_FOUND
        }
        if (searchStr.length == 0) {
            return startPos
        }
        for (i in startPos downTo 0) {
            if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, searchStr.length)) {
                return i
            }
        }
        return INDEX_NOT_FOUND
    }
    // Contains
//-----------------------------------------------------------------------
    /**
     *
     * Checks if CharSequence contains a search character, handling `null`.
     * This method uses [String.indexOf] if possible.
     *
     *
     * A `null` or empty ("") CharSequence will return `false`.
     *
     * <pre>
     * StringUtils.contains(null, *)    = false
     * StringUtils.contains("", *)      = false
     * StringUtils.contains("abc", 'a') = true
     * StringUtils.contains("abc", 'z') = false
    </pre> *
     *
     * @param seq  the CharSequence to check, may be null
     * @param searchChar  the character to find
     * @return true if the CharSequence contains the search character,
     * false if not or `null` string input
     * @since 2.0
     * @since 3.0 Changed signature from contains(String, int) to contains(CharSequence, int)
     */
    fun contains(seq: CharSequence?, searchChar: Int): Boolean {
        return if (isEmpty(seq)) {
            false
        } else CharSequenceUtils.indexOf(seq, searchChar, 0) >= 0
    }

    /**
     *
     * Checks if CharSequence contains a search CharSequence, handling `null`.
     * This method uses [String.indexOf] if possible.
     *
     *
     * A `null` CharSequence will return `false`.
     *
     * <pre>
     * StringUtils.contains(null, *)     = false
     * StringUtils.contains(*, null)     = false
     * StringUtils.contains("", "")      = true
     * StringUtils.contains("abc", "")   = true
     * StringUtils.contains("abc", "a")  = true
     * StringUtils.contains("abc", "z")  = false
    </pre> *
     *
     * @param seq  the CharSequence to check, may be null
     * @param searchSeq  the CharSequence to find, may be null
     * @return true if the CharSequence contains the search CharSequence,
     * false if not or `null` string input
     * @since 2.0
     * @since 3.0 Changed signature from contains(String, String) to contains(CharSequence, CharSequence)
     */
    fun contains(seq: CharSequence?, searchSeq: CharSequence?): Boolean {
        return if (seq == null || searchSeq == null) {
            false
        } else CharSequenceUtils.indexOf(seq, searchSeq, 0) >= 0
    }

    /**
     *
     * Checks if CharSequence contains a search CharSequence irrespective of case,
     * handling `null`. Case-insensitivity is defined as by
     * [String.equalsIgnoreCase].
     *
     *
     * A `null` CharSequence will return `false`.
     *
     * <pre>
     * StringUtils.containsIgnoreCase(null, *) = false
     * StringUtils.containsIgnoreCase(*, null) = false
     * StringUtils.containsIgnoreCase("", "") = true
     * StringUtils.containsIgnoreCase("abc", "") = true
     * StringUtils.containsIgnoreCase("abc", "a") = true
     * StringUtils.containsIgnoreCase("abc", "z") = false
     * StringUtils.containsIgnoreCase("abc", "A") = true
     * StringUtils.containsIgnoreCase("abc", "Z") = false
    </pre> *
     *
     * @param str  the CharSequence to check, may be null
     * @param searchStr  the CharSequence to find, may be null
     * @return true if the CharSequence contains the search CharSequence irrespective of
     * case or false if not or `null` string input
     * @since 3.0 Changed signature from containsIgnoreCase(String, String) to containsIgnoreCase(CharSequence, CharSequence)
     */
    fun containsIgnoreCase(str: CharSequence?, searchStr: CharSequence?): Boolean {
        if (str == null || searchStr == null) {
            return false
        }
        val len = searchStr.length
        val max = str.length - len
        for (i in 0..max) {
            if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, len)) {
                return true
            }
        }
        return false
    }

    /**
     *
     * Check whether the given CharSequence contains any whitespace characters.
     *
     *
     * Whitespace is defined by [Character.isWhitespace].
     *
     * @param seq the CharSequence to check (may be `null`)
     * @return `true` if the CharSequence is not empty and
     * contains at least 1 (breaking) whitespace character
     * @since 3.0
     */
// From org.springframework.util.StringUtils, under Apache License 2.0
    fun containsWhitespace(seq: CharSequence): Boolean {
        if (isEmpty(seq)) {
            return false
        }
        val strLen = seq.length
        for (i in 0 until strLen) {
            if (Character.isWhitespace(seq[i])) {
                return true
            }
        }
        return false
    }
    // IndexOfAny chars
//-----------------------------------------------------------------------
    /**
     *
     * Search a CharSequence to find the first index of any
     * character in the given set of characters.
     *
     *
     * A `null` String will return `-1`.
     * A `null` or zero length search array will return `-1`.
     *
     * <pre>
     * StringUtils.indexOfAny(null, *)                  = -1
     * StringUtils.indexOfAny("", *)                    = -1
     * StringUtils.indexOfAny(*, null)                  = -1
     * StringUtils.indexOfAny(*, [])                    = -1
     * StringUtils.indexOfAny("zzabyycdxx", ['z', 'a']) = 0
     * StringUtils.indexOfAny("zzabyycdxx", ['b', 'y']) = 3
     * StringUtils.indexOfAny("aba", ['z'])             = -1
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @param searchChars  the chars to search for, may be null
     * @return the index of any of the chars, -1 if no match or null input
     * @since 2.0
     * @since 3.0 Changed signature from indexOfAny(String, char[]) to indexOfAny(CharSequence, char...)
     */
    fun indexOfAny(cs: CharSequence, vararg searchChars: Char): Int {
        if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
            return INDEX_NOT_FOUND
        }
        val csLen = cs.length
        val csLast = csLen - 1
        val searchLen = searchChars.size
        val searchLast = searchLen - 1
        for (i in 0 until csLen) {
            val ch = cs[i]
            for (j in 0 until searchLen) {
                if (searchChars[j] == ch) {
                    if (i < csLast && j < searchLast && Character.isHighSurrogate(ch)) { // ch is a supplementary character
                        if (searchChars[j + 1] == cs[i + 1]) {
                            return i
                        }
                    } else {
                        return i
                    }
                }
            }
        }
        return INDEX_NOT_FOUND
    }

    /**
     *
     * Search a CharSequence to find the first index of any
     * character in the given set of characters.
     *
     *
     * A `null` String will return `-1`.
     * A `null` search string will return `-1`.
     *
     * <pre>
     * StringUtils.indexOfAny(null, *)            = -1
     * StringUtils.indexOfAny("", *)              = -1
     * StringUtils.indexOfAny(*, null)            = -1
     * StringUtils.indexOfAny(*, "")              = -1
     * StringUtils.indexOfAny("zzabyycdxx", "za") = 0
     * StringUtils.indexOfAny("zzabyycdxx", "by") = 3
     * StringUtils.indexOfAny("aba", "z")         = -1
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @param searchChars  the chars to search for, may be null
     * @return the index of any of the chars, -1 if no match or null input
     * @since 2.0
     * @since 3.0 Changed signature from indexOfAny(String, String) to indexOfAny(CharSequence, String)
     */
    fun indexOfAny(cs: CharSequence, searchChars: String): Int {
        return if (isEmpty(cs) || isEmpty(searchChars)) {
            INDEX_NOT_FOUND
        } else indexOfAny(cs, *searchChars.toCharArray())
    }
    // ContainsAny
//-----------------------------------------------------------------------
    /**
     *
     * Checks if the CharSequence contains any character in the given
     * set of characters.
     *
     *
     * A `null` CharSequence will return `false`.
     * A `null` or zero length search array will return `false`.
     *
     * <pre>
     * StringUtils.containsAny(null, *)                  = false
     * StringUtils.containsAny("", *)                    = false
     * StringUtils.containsAny(*, null)                  = false
     * StringUtils.containsAny(*, [])                    = false
     * StringUtils.containsAny("zzabyycdxx", ['z', 'a']) = true
     * StringUtils.containsAny("zzabyycdxx", ['b', 'y']) = true
     * StringUtils.containsAny("zzabyycdxx", ['z', 'y']) = true
     * StringUtils.containsAny("aba", ['z'])             = false
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @param searchChars  the chars to search for, may be null
     * @return the `true` if any of the chars are found,
     * `false` if no match or null input
     * @since 2.4
     * @since 3.0 Changed signature from containsAny(String, char[]) to containsAny(CharSequence, char...)
     */
    fun containsAny(cs: CharSequence, vararg searchChars: Char): Boolean {
        if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
            return false
        }
        val csLength = cs.length
        val searchLength = searchChars.size
        val csLast = csLength - 1
        val searchLast = searchLength - 1
        for (i in 0 until csLength) {
            val ch = cs[i]
            for (j in 0 until searchLength) {
                if (searchChars[j] == ch) {
                    if (Character.isHighSurrogate(ch)) {
                        if (j == searchLast) { // missing low surrogate, fine, like String.indexOf(String)
                            return true
                        }
                        if (i < csLast && searchChars[j + 1] == cs[i + 1]) {
                            return true
                        }
                    } else { // ch is in the Basic Multilingual Plane
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     *
     *
     * Checks if the CharSequence contains any character in the given set of characters.
     *
     *
     *
     *
     * A `null` CharSequence will return `false`. A `null` search CharSequence will return
     * `false`.
     *
     *
     * <pre>
     * StringUtils.containsAny(null, *)               = false
     * StringUtils.containsAny("", *)                 = false
     * StringUtils.containsAny(*, null)               = false
     * StringUtils.containsAny(*, "")                 = false
     * StringUtils.containsAny("zzabyycdxx", "za")    = true
     * StringUtils.containsAny("zzabyycdxx", "by")    = true
     * StringUtils.containsAny("zzabyycdxx", "zy")    = true
     * StringUtils.containsAny("zzabyycdxx", "\tx")   = true
     * StringUtils.containsAny("zzabyycdxx", "$.#yF") = true
     * StringUtils.containsAny("aba", "z")            = false
    </pre> *
     *
     * @param cs
     * the CharSequence to check, may be null
     * @param searchChars
     * the chars to search for, may be null
     * @return the `true` if any of the chars are found, `false` if no match or null input
     * @since 2.4
     * @since 3.0 Changed signature from containsAny(String, String) to containsAny(CharSequence, CharSequence)
     */
    fun containsAny(cs: CharSequence, searchChars: CharSequence?): Boolean {
        return if (searchChars == null) {
            false
        } else containsAny(cs, *CharSequenceUtils.toCharArray(searchChars))
    }

    /**
     *
     * Checks if the CharSequence contains any of the CharSequences in the given array.
     *
     *
     *
     * A `null` `cs` CharSequence will return `false`. A `null` or zero
     * length search array will return `false`.
     *
     *
     * <pre>
     * StringUtils.containsAny(null, *)            = false
     * StringUtils.containsAny("", *)              = false
     * StringUtils.containsAny(*, null)            = false
     * StringUtils.containsAny(*, [])              = false
     * StringUtils.containsAny("abcd", "ab", null) = true
     * StringUtils.containsAny("abcd", "ab", "cd") = true
     * StringUtils.containsAny("abc", "d", "abc")  = true
    </pre> *
     *
     *
     * @param cs The CharSequence to check, may be null
     * @param searchCharSequences The array of CharSequences to search for, may be null.
     * Individual CharSequences may be null as well.
     * @return `true` if any of the search CharSequences are found, `false` otherwise
     * @since 3.4
     */
    fun containsAny(cs: CharSequence?, vararg searchCharSequences: CharSequence?): Boolean {
        if (isEmpty(cs) || ArrayUtils.isEmpty(searchCharSequences)) {
            return false
        }
        for (searchCharSequence in searchCharSequences) {
            if (contains(cs, searchCharSequence)) {
                return true
            }
        }
        return false
    }
    // IndexOfAnyBut chars
//-----------------------------------------------------------------------
    /**
     *
     * Searches a CharSequence to find the first index of any
     * character not in the given set of characters.
     *
     *
     * A `null` CharSequence will return `-1`.
     * A `null` or zero length search array will return `-1`.
     *
     * <pre>
     * StringUtils.indexOfAnyBut(null, *)                              = -1
     * StringUtils.indexOfAnyBut("", *)                                = -1
     * StringUtils.indexOfAnyBut(*, null)                              = -1
     * StringUtils.indexOfAnyBut(*, [])                                = -1
     * StringUtils.indexOfAnyBut("zzabyycdxx", new char[] {'z', 'a'} ) = 3
     * StringUtils.indexOfAnyBut("aba", new char[] {'z'} )             = 0
     * StringUtils.indexOfAnyBut("aba", new char[] {'a', 'b'} )        = -1
     *
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @param searchChars  the chars to search for, may be null
     * @return the index of any of the chars, -1 if no match or null input
     * @since 2.0
     * @since 3.0 Changed signature from indexOfAnyBut(String, char[]) to indexOfAnyBut(CharSequence, char...)
     */
    fun indexOfAnyBut(cs: CharSequence, vararg searchChars: Char): Int {
        if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
            return INDEX_NOT_FOUND
        }
        val csLen = cs.length
        val csLast = csLen - 1
        val searchLen = searchChars.size
        val searchLast = searchLen - 1
        outer@ for (i in 0 until csLen) {
            val ch = cs[i]
            for (j in 0 until searchLen) {
                if (searchChars[j] == ch) {
                    if (i < csLast && j < searchLast && Character.isHighSurrogate(ch)) {
                        if (searchChars[j + 1] == cs[i + 1]) {
                            continue@outer
                        }
                    } else {
                        continue@outer
                    }
                }
            }
            return i
        }
        return INDEX_NOT_FOUND
    }

    /**
     *
     * Search a CharSequence to find the first index of any
     * character not in the given set of characters.
     *
     *
     * A `null` CharSequence will return `-1`.
     * A `null` or empty search string will return `-1`.
     *
     * <pre>
     * StringUtils.indexOfAnyBut(null, *)            = -1
     * StringUtils.indexOfAnyBut("", *)              = -1
     * StringUtils.indexOfAnyBut(*, null)            = -1
     * StringUtils.indexOfAnyBut(*, "")              = -1
     * StringUtils.indexOfAnyBut("zzabyycdxx", "za") = 3
     * StringUtils.indexOfAnyBut("zzabyycdxx", "")   = -1
     * StringUtils.indexOfAnyBut("aba", "ab")        = -1
    </pre> *
     *
     * @param seq  the CharSequence to check, may be null
     * @param searchChars  the chars to search for, may be null
     * @return the index of any of the chars, -1 if no match or null input
     * @since 2.0
     * @since 3.0 Changed signature from indexOfAnyBut(String, String) to indexOfAnyBut(CharSequence, CharSequence)
     */
    fun indexOfAnyBut(seq: CharSequence, searchChars: CharSequence?): Int {
        if (isEmpty(seq) || isEmpty(searchChars)) {
            return INDEX_NOT_FOUND
        }
        val strLen = seq.length
        for (i in 0 until strLen) {
            val ch = seq[i]
            val chFound = CharSequenceUtils.indexOf(searchChars, ch.toInt(), 0) >= 0
            if (i + 1 < strLen && Character.isHighSurrogate(ch)) {
                val ch2 = seq[i + 1]
                if (chFound && CharSequenceUtils.indexOf(searchChars, ch2.toInt(), 0) < 0) {
                    return i
                }
            } else {
                if (!chFound) {
                    return i
                }
            }
        }
        return INDEX_NOT_FOUND
    }
    // ContainsOnly
//-----------------------------------------------------------------------
    /**
     *
     * Checks if the CharSequence contains only certain characters.
     *
     *
     * A `null` CharSequence will return `false`.
     * A `null` valid character array will return `false`.
     * An empty CharSequence (length()=0) always returns `true`.
     *
     * <pre>
     * StringUtils.containsOnly(null, *)       = false
     * StringUtils.containsOnly(*, null)       = false
     * StringUtils.containsOnly("", *)         = true
     * StringUtils.containsOnly("ab", '')      = false
     * StringUtils.containsOnly("abab", 'abc') = true
     * StringUtils.containsOnly("ab1", 'abc')  = false
     * StringUtils.containsOnly("abz", 'abc')  = false
    </pre> *
     *
     * @param cs  the String to check, may be null
     * @param valid  an array of valid chars, may be null
     * @return true if it only contains valid chars and is non-null
     * @since 3.0 Changed signature from containsOnly(String, char[]) to containsOnly(CharSequence, char...)
     */
    fun containsOnly(
        cs: CharSequence?,
        vararg valid: Char
    ): Boolean { // All these pre-checks are to maintain API with an older version
        if (valid == null || cs == null) {
            return false
        }
        if (cs.length == 0) {
            return true
        }
        return if (valid.size == 0) {
            false
        } else indexOfAnyBut(cs, *valid) == INDEX_NOT_FOUND
    }

    /**
     *
     * Checks if the CharSequence contains only certain characters.
     *
     *
     * A `null` CharSequence will return `false`.
     * A `null` valid character String will return `false`.
     * An empty String (length()=0) always returns `true`.
     *
     * <pre>
     * StringUtils.containsOnly(null, *)       = false
     * StringUtils.containsOnly(*, null)       = false
     * StringUtils.containsOnly("", *)         = true
     * StringUtils.containsOnly("ab", "")      = false
     * StringUtils.containsOnly("abab", "abc") = true
     * StringUtils.containsOnly("ab1", "abc")  = false
     * StringUtils.containsOnly("abz", "abc")  = false
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @param validChars  a String of valid chars, may be null
     * @return true if it only contains valid chars and is non-null
     * @since 2.0
     * @since 3.0 Changed signature from containsOnly(String, String) to containsOnly(CharSequence, String)
     */
    fun containsOnly(cs: CharSequence?, validChars: String?): Boolean {
        return if (cs == null || validChars == null) {
            false
        } else containsOnly(cs, *validChars.toCharArray())
    }
    // ContainsNone
//-----------------------------------------------------------------------
    /**
     *
     * Checks that the CharSequence does not contain certain characters.
     *
     *
     * A `null` CharSequence will return `true`.
     * A `null` invalid character array will return `true`.
     * An empty CharSequence (length()=0) always returns true.
     *
     * <pre>
     * StringUtils.containsNone(null, *)       = true
     * StringUtils.containsNone(*, null)       = true
     * StringUtils.containsNone("", *)         = true
     * StringUtils.containsNone("ab", '')      = true
     * StringUtils.containsNone("abab", 'xyz') = true
     * StringUtils.containsNone("ab1", 'xyz')  = true
     * StringUtils.containsNone("abz", 'xyz')  = false
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @param searchChars  an array of invalid chars, may be null
     * @return true if it contains none of the invalid chars, or is null
     * @since 2.0
     * @since 3.0 Changed signature from containsNone(String, char[]) to containsNone(CharSequence, char...)
     */
    fun containsNone(cs: CharSequence?, vararg searchChars: Char): Boolean {
        if (cs == null || searchChars == null) {
            return true
        }
        val csLen = cs.length
        val csLast = csLen - 1
        val searchLen = searchChars.size
        val searchLast = searchLen - 1
        for (i in 0 until csLen) {
            val ch = cs[i]
            for (j in 0 until searchLen) {
                if (searchChars[j] == ch) {
                    if (Character.isHighSurrogate(ch)) {
                        if (j == searchLast) { // missing low surrogate, fine, like String.indexOf(String)
                            return false
                        }
                        if (i < csLast && searchChars[j + 1] == cs[i + 1]) {
                            return false
                        }
                    } else { // ch is in the Basic Multilingual Plane
                        return false
                    }
                }
            }
        }
        return true
    }

    /**
     *
     * Checks that the CharSequence does not contain certain characters.
     *
     *
     * A `null` CharSequence will return `true`.
     * A `null` invalid character array will return `true`.
     * An empty String ("") always returns true.
     *
     * <pre>
     * StringUtils.containsNone(null, *)       = true
     * StringUtils.containsNone(*, null)       = true
     * StringUtils.containsNone("", *)         = true
     * StringUtils.containsNone("ab", "")      = true
     * StringUtils.containsNone("abab", "xyz") = true
     * StringUtils.containsNone("ab1", "xyz")  = true
     * StringUtils.containsNone("abz", "xyz")  = false
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @param invalidChars  a String of invalid chars, may be null
     * @return true if it contains none of the invalid chars, or is null
     * @since 2.0
     * @since 3.0 Changed signature from containsNone(String, String) to containsNone(CharSequence, String)
     */
    fun containsNone(cs: CharSequence?, invalidChars: String?): Boolean {
        return if (cs == null || invalidChars == null) {
            true
        } else containsNone(cs, *invalidChars.toCharArray())
    }
    // IndexOfAny strings
//-----------------------------------------------------------------------
    /**
     *
     * Find the first index of any of a set of potential substrings.
     *
     *
     * A `null` CharSequence will return `-1`.
     * A `null` or zero length search array will return `-1`.
     * A `null` search array entry will be ignored, but a search
     * array containing "" will return `0` if `str` is not
     * null. This method uses [String.indexOf] if possible.
     *
     * <pre>
     * StringUtils.indexOfAny(null, *)                      = -1
     * StringUtils.indexOfAny(*, null)                      = -1
     * StringUtils.indexOfAny(*, [])                        = -1
     * StringUtils.indexOfAny("zzabyycdxx", ["ab", "cd"])   = 2
     * StringUtils.indexOfAny("zzabyycdxx", ["cd", "ab"])   = 2
     * StringUtils.indexOfAny("zzabyycdxx", ["mn", "op"])   = -1
     * StringUtils.indexOfAny("zzabyycdxx", ["zab", "aby"]) = 1
     * StringUtils.indexOfAny("zzabyycdxx", [""])           = 0
     * StringUtils.indexOfAny("", [""])                     = 0
     * StringUtils.indexOfAny("", ["a"])                    = -1
    </pre> *
     *
     * @param str  the CharSequence to check, may be null
     * @param searchStrs  the CharSequences to search for, may be null
     * @return the first index of any of the searchStrs in str, -1 if no match
     * @since 3.0 Changed signature from indexOfAny(String, String[]) to indexOfAny(CharSequence, CharSequence...)
     */
    fun indexOfAny(str: CharSequence?, vararg searchStrs: CharSequence?): Int {
        if (str == null || searchStrs == null) {
            return INDEX_NOT_FOUND
        }
        // String's can't have a MAX_VALUEth index.
        var ret = Int.MAX_VALUE
        var tmp = 0
        for (search in searchStrs) {
            if (search == null) {
                continue
            }
            tmp = CharSequenceUtils.indexOf(str, search, 0)
            if (tmp == INDEX_NOT_FOUND) {
                continue
            }
            if (tmp < ret) {
                ret = tmp
            }
        }
        return if (ret == Int.MAX_VALUE) INDEX_NOT_FOUND else ret
    }

    /**
     *
     * Find the latest index of any of a set of potential substrings.
     *
     *
     * A `null` CharSequence will return `-1`.
     * A `null` search array will return `-1`.
     * A `null` or zero length search array entry will be ignored,
     * but a search array containing "" will return the length of `str`
     * if `str` is not null. This method uses [String.indexOf] if possible
     *
     * <pre>
     * StringUtils.lastIndexOfAny(null, *)                    = -1
     * StringUtils.lastIndexOfAny(*, null)                    = -1
     * StringUtils.lastIndexOfAny(*, [])                      = -1
     * StringUtils.lastIndexOfAny(*, [null])                  = -1
     * StringUtils.lastIndexOfAny("zzabyycdxx", ["ab", "cd"]) = 6
     * StringUtils.lastIndexOfAny("zzabyycdxx", ["cd", "ab"]) = 6
     * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn", "op"]) = -1
     * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn", "op"]) = -1
     * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn", ""])   = 10
    </pre> *
     *
     * @param str  the CharSequence to check, may be null
     * @param searchStrs  the CharSequences to search for, may be null
     * @return the last index of any of the CharSequences, -1 if no match
     * @since 3.0 Changed signature from lastIndexOfAny(String, String[]) to lastIndexOfAny(CharSequence, CharSequence)
     */
    fun lastIndexOfAny(str: CharSequence?, vararg searchStrs: CharSequence?): Int {
        if (str == null || searchStrs == null) {
            return INDEX_NOT_FOUND
        }
        var ret = INDEX_NOT_FOUND
        var tmp = 0
        for (search in searchStrs) {
            if (search == null) {
                continue
            }
            tmp = CharSequenceUtils.lastIndexOf(str, search, str.length)
            if (tmp > ret) {
                ret = tmp
            }
        }
        return ret
    }
    // Substring
//-----------------------------------------------------------------------
    /**
     *
     * Gets a substring from the specified String avoiding exceptions.
     *
     *
     * A negative start position can be used to start `n`
     * characters from the end of the String.
     *
     *
     * A `null` String will return `null`.
     * An empty ("") String will return "".
     *
     * <pre>
     * StringUtils.substring(null, *)   = null
     * StringUtils.substring("", *)     = ""
     * StringUtils.substring("abc", 0)  = "abc"
     * StringUtils.substring("abc", 2)  = "c"
     * StringUtils.substring("abc", 4)  = ""
     * StringUtils.substring("abc", -2) = "bc"
     * StringUtils.substring("abc", -4) = "abc"
    </pre> *
     *
     * @param str  the String to get the substring from, may be null
     * @param start  the position to start from, negative means
     * count back from the end of the String by this many characters
     * @return substring from start position, `null` if null String input
     */
    fun substring(str: String?, start: Int): String? {
        var start = start
        if (str == null) {
            return null
        }
        // handle negatives, which means last n characters
        if (start < 0) {
            start = str.length + start // remember start is negative
        }
        if (start < 0) {
            start = 0
        }
        return if (start > str.length) {
            EMPTY
        } else str.substring(start)
    }

    /**
     *
     * Gets a substring from the specified String avoiding exceptions.
     *
     *
     * A negative start position can be used to start/end `n`
     * characters from the end of the String.
     *
     *
     * The returned substring starts with the character in the `start`
     * position and ends before the `end` position. All position counting is
     * zero-based -- i.e., to start at the beginning of the string use
     * `start = 0`. Negative start and end positions can be used to
     * specify offsets relative to the end of the String.
     *
     *
     * If `start` is not strictly to the left of `end`, ""
     * is returned.
     *
     * <pre>
     * StringUtils.substring(null, *, *)    = null
     * StringUtils.substring("", * ,  *)    = "";
     * StringUtils.substring("abc", 0, 2)   = "ab"
     * StringUtils.substring("abc", 2, 0)   = ""
     * StringUtils.substring("abc", 2, 4)   = "c"
     * StringUtils.substring("abc", 4, 6)   = ""
     * StringUtils.substring("abc", 2, 2)   = ""
     * StringUtils.substring("abc", -2, -1) = "b"
     * StringUtils.substring("abc", -4, 2)  = "ab"
    </pre> *
     *
     * @param str  the String to get the substring from, may be null
     * @param start  the position to start from, negative means
     * count back from the end of the String by this many characters
     * @param end  the position to end at (exclusive), negative means
     * count back from the end of the String by this many characters
     * @return substring from start position to end position,
     * `null` if null String input
     */
    fun substring(str: String?, start: Int, end: Int): String? {
        var start = start
        var end = end
        if (str == null) {
            return null
        }
        // handle negatives
        if (end < 0) {
            end = str.length + end // remember end is negative
        }
        if (start < 0) {
            start = str.length + start // remember start is negative
        }
        // check length next
        if (end > str.length) {
            end = str.length
        }
        // if start is greater than end, return ""
        if (start > end) {
            return EMPTY
        }
        if (start < 0) {
            start = 0
        }
        if (end < 0) {
            end = 0
        }
        return str.substring(start, end)
    }
    // Left/Right/Mid
//-----------------------------------------------------------------------
    /**
     *
     * Gets the leftmost `len` characters of a String.
     *
     *
     * If `len` characters are not available, or the
     * String is `null`, the String will be returned without
     * an exception. An empty String is returned if len is negative.
     *
     * <pre>
     * StringUtils.left(null, *)    = null
     * StringUtils.left(*, -ve)     = ""
     * StringUtils.left("", *)      = ""
     * StringUtils.left("abc", 0)   = ""
     * StringUtils.left("abc", 2)   = "ab"
     * StringUtils.left("abc", 4)   = "abc"
    </pre> *
     *
     * @param str  the String to get the leftmost characters from, may be null
     * @param len  the length of the required String
     * @return the leftmost characters, `null` if null String input
     */
    fun left(str: String?, len: Int): String? {
        if (str == null) {
            return null
        }
        if (len < 0) {
            return EMPTY
        }
        return if (str.length <= len) {
            str
        } else str.substring(0, len)
    }

    /**
     *
     * Gets the rightmost `len` characters of a String.
     *
     *
     * If `len` characters are not available, or the String
     * is `null`, the String will be returned without an
     * an exception. An empty String is returned if len is negative.
     *
     * <pre>
     * StringUtils.right(null, *)    = null
     * StringUtils.right(*, -ve)     = ""
     * StringUtils.right("", *)      = ""
     * StringUtils.right("abc", 0)   = ""
     * StringUtils.right("abc", 2)   = "bc"
     * StringUtils.right("abc", 4)   = "abc"
    </pre> *
     *
     * @param str  the String to get the rightmost characters from, may be null
     * @param len  the length of the required String
     * @return the rightmost characters, `null` if null String input
     */
    fun right(str: String?, len: Int): String? {
        if (str == null) {
            return null
        }
        if (len < 0) {
            return EMPTY
        }
        return if (str.length <= len) {
            str
        } else str.substring(str.length - len)
    }

    /**
     *
     * Gets `len` characters from the middle of a String.
     *
     *
     * If `len` characters are not available, the remainder
     * of the String will be returned without an exception. If the
     * String is `null`, `null` will be returned.
     * An empty String is returned if len is negative or exceeds the
     * length of `str`.
     *
     * <pre>
     * StringUtils.mid(null, *, *)    = null
     * StringUtils.mid(*, *, -ve)     = ""
     * StringUtils.mid("", 0, *)      = ""
     * StringUtils.mid("abc", 0, 2)   = "ab"
     * StringUtils.mid("abc", 0, 4)   = "abc"
     * StringUtils.mid("abc", 2, 4)   = "c"
     * StringUtils.mid("abc", 4, 2)   = ""
     * StringUtils.mid("abc", -2, 2)  = "ab"
    </pre> *
     *
     * @param str  the String to get the characters from, may be null
     * @param pos  the position to start from, negative treated as zero
     * @param len  the length of the required String
     * @return the middle characters, `null` if null String input
     */
    fun mid(str: String?, pos: Int, len: Int): String? {
        var pos = pos
        if (str == null) {
            return null
        }
        if (len < 0 || pos > str.length) {
            return EMPTY
        }
        if (pos < 0) {
            pos = 0
        }
        return if (str.length <= pos + len) {
            str.substring(pos)
        } else str.substring(pos, pos + len)
    }

    private fun newStringBuilder(noOfItems: Int): StringBuilder {
        return StringBuilder(noOfItems * 16)
    }
    // SubStringAfter/SubStringBefore
//-----------------------------------------------------------------------
    /**
     *
     * Gets the substring before the first occurrence of a separator.
     * The separator is not returned.
     *
     *
     * A `null` string input will return `null`.
     * An empty ("") string input will return the empty string.
     * A `null` separator will return the input string.
     *
     *
     * If nothing is found, the string input is returned.
     *
     * <pre>
     * StringUtils.substringBefore(null, *)      = null
     * StringUtils.substringBefore("", *)        = ""
     * StringUtils.substringBefore("abc", "a")   = ""
     * StringUtils.substringBefore("abcba", "b") = "a"
     * StringUtils.substringBefore("abc", "c")   = "ab"
     * StringUtils.substringBefore("abc", "d")   = "abc"
     * StringUtils.substringBefore("abc", "")    = ""
     * StringUtils.substringBefore("abc", null)  = "abc"
    </pre> *
     *
     * @param str  the String to get a substring from, may be null
     * @param separator  the String to search for, may be null
     * @return the substring before the first occurrence of the separator,
     * `null` if null String input
     * @since 2.0
     */
    fun substringBefore(str: String, separator: String?): String {
        if (isEmpty(str) || separator == null) {
            return str
        }
        if (separator.isEmpty()) {
            return EMPTY
        }
        val pos = str.indexOf(separator)
        return if (pos == INDEX_NOT_FOUND) {
            str
        } else str.substring(0, pos)
    }

    /**
     *
     * Gets the substring after the first occurrence of a separator.
     * The separator is not returned.
     *
     *
     * A `null` string input will return `null`.
     * An empty ("") string input will return the empty string.
     * A `null` separator will return the empty string if the
     * input string is not `null`.
     *
     *
     * If nothing is found, the empty string is returned.
     *
     * <pre>
     * StringUtils.substringAfter(null, *)      = null
     * StringUtils.substringAfter("", *)        = ""
     * StringUtils.substringAfter(*, null)      = ""
     * StringUtils.substringAfter("abc", "a")   = "bc"
     * StringUtils.substringAfter("abcba", "b") = "cba"
     * StringUtils.substringAfter("abc", "c")   = ""
     * StringUtils.substringAfter("abc", "d")   = ""
     * StringUtils.substringAfter("abc", "")    = "abc"
    </pre> *
     *
     * @param str  the String to get a substring from, may be null
     * @param separator  the String to search for, may be null
     * @return the substring after the first occurrence of the separator,
     * `null` if null String input
     * @since 2.0
     */
    fun substringAfter(str: String, separator: String?): String {
        if (isEmpty(str)) {
            return str
        }
        if (separator == null) {
            return EMPTY
        }
        val pos = str.indexOf(separator)
        return if (pos == INDEX_NOT_FOUND) {
            EMPTY
        } else str.substring(pos + separator.length)
    }

    /**
     *
     * Gets the substring before the last occurrence of a separator.
     * The separator is not returned.
     *
     *
     * A `null` string input will return `null`.
     * An empty ("") string input will return the empty string.
     * An empty or `null` separator will return the input string.
     *
     *
     * If nothing is found, the string input is returned.
     *
     * <pre>
     * StringUtils.substringBeforeLast(null, *)      = null
     * StringUtils.substringBeforeLast("", *)        = ""
     * StringUtils.substringBeforeLast("abcba", "b") = "abc"
     * StringUtils.substringBeforeLast("abc", "c")   = "ab"
     * StringUtils.substringBeforeLast("a", "a")     = ""
     * StringUtils.substringBeforeLast("a", "z")     = "a"
     * StringUtils.substringBeforeLast("a", null)    = "a"
     * StringUtils.substringBeforeLast("a", "")      = "a"
    </pre> *
     *
     * @param str  the String to get a substring from, may be null
     * @param separator  the String to search for, may be null
     * @return the substring before the last occurrence of the separator,
     * `null` if null String input
     * @since 2.0
     */
    fun substringBeforeLast(str: String, separator: String?): String {
        if (isEmpty(str) || isEmpty(separator)) {
            return str
        }
        val pos = str.lastIndexOf(separator!!)
        return if (pos == INDEX_NOT_FOUND) {
            str
        } else str.substring(0, pos)
    }

    /**
     *
     * Gets the substring after the last occurrence of a separator.
     * The separator is not returned.
     *
     *
     * A `null` string input will return `null`.
     * An empty ("") string input will return the empty string.
     * An empty or `null` separator will return the empty string if
     * the input string is not `null`.
     *
     *
     * If nothing is found, the empty string is returned.
     *
     * <pre>
     * StringUtils.substringAfterLast(null, *)      = null
     * StringUtils.substringAfterLast("", *)        = ""
     * StringUtils.substringAfterLast(*, "")        = ""
     * StringUtils.substringAfterLast(*, null)      = ""
     * StringUtils.substringAfterLast("abc", "a")   = "bc"
     * StringUtils.substringAfterLast("abcba", "b") = "a"
     * StringUtils.substringAfterLast("abc", "c")   = ""
     * StringUtils.substringAfterLast("a", "a")     = ""
     * StringUtils.substringAfterLast("a", "z")     = ""
    </pre> *
     *
     * @param str  the String to get a substring from, may be null
     * @param separator  the String to search for, may be null
     * @return the substring after the last occurrence of the separator,
     * `null` if null String input
     * @since 2.0
     */
    fun substringAfterLast(str: String, separator: String): String {
        if (isEmpty(str)) {
            return str
        }
        if (isEmpty(separator)) {
            return EMPTY
        }
        val pos = str.lastIndexOf(separator)
        return if (pos == INDEX_NOT_FOUND || pos == str.length - separator.length) {
            EMPTY
        } else str.substring(pos + separator.length)
    }
    // Substring between
//-----------------------------------------------------------------------
    /**
     *
     * Gets the String that is nested in between two instances of the
     * same String.
     *
     *
     * A `null` input String returns `null`.
     * A `null` tag returns `null`.
     *
     * <pre>
     * StringUtils.substringBetween(null, *)            = null
     * StringUtils.substringBetween("", "")             = ""
     * StringUtils.substringBetween("", "tag")          = null
     * StringUtils.substringBetween("tagabctag", null)  = null
     * StringUtils.substringBetween("tagabctag", "")    = ""
     * StringUtils.substringBetween("tagabctag", "tag") = "abc"
    </pre> *
     *
     * @param str  the String containing the substring, may be null
     * @param tag  the String before and after the substring, may be null
     * @return the substring, `null` if no match
     * @since 2.0
     */
    fun substringBetween(str: String?, tag: String?): String? {
        return substringBetween(str, tag, tag)
    }

    /**
     *
     * Gets the String that is nested in between two Strings.
     * Only the first match is returned.
     *
     *
     * A `null` input String returns `null`.
     * A `null` open/close returns `null` (no match).
     * An empty ("") open and close returns an empty string.
     *
     * <pre>
     * StringUtils.substringBetween("wx[b]yz", "[", "]") = "b"
     * StringUtils.substringBetween(null, *, *)          = null
     * StringUtils.substringBetween(*, null, *)          = null
     * StringUtils.substringBetween(*, *, null)          = null
     * StringUtils.substringBetween("", "", "")          = ""
     * StringUtils.substringBetween("", "", "]")         = null
     * StringUtils.substringBetween("", "[", "]")        = null
     * StringUtils.substringBetween("yabcz", "", "")     = ""
     * StringUtils.substringBetween("yabcz", "y", "z")   = "abc"
     * StringUtils.substringBetween("yabczyabcz", "y", "z")   = "abc"
    </pre> *
     *
     * @param str  the String containing the substring, may be null
     * @param open  the String before the substring, may be null
     * @param close  the String after the substring, may be null
     * @return the substring, `null` if no match
     * @since 2.0
     */
    fun substringBetween(str: String?, open: String?, close: String?): String? {
        if (str == null || open == null || close == null) {
            return null
        }
        val start = str.indexOf(open)
        if (start != INDEX_NOT_FOUND) {
            val end = str.indexOf(close, start + open.length)
            if (end != INDEX_NOT_FOUND) {
                return str.substring(start + open.length, end)
            }
        }
        return null
    }

    /**
     *
     * Searches a String for substrings delimited by a start and end tag,
     * returning all matching substrings in an array.
     *
     *
     * A `null` input String returns `null`.
     * A `null` open/close returns `null` (no match).
     * An empty ("") open/close returns `null` (no match).
     *
     * <pre>
     * StringUtils.substringsBetween("[a][b][c]", "[", "]") = ["a","b","c"]
     * StringUtils.substringsBetween(null, *, *)            = null
     * StringUtils.substringsBetween(*, null, *)            = null
     * StringUtils.substringsBetween(*, *, null)            = null
     * StringUtils.substringsBetween("", "[", "]")          = []
    </pre> *
     *
     * @param str  the String containing the substrings, null returns null, empty returns empty
     * @param open  the String identifying the start of the substring, empty returns null
     * @param close  the String identifying the end of the substring, empty returns null
     * @return a String Array of substrings, or `null` if no match
     * @since 2.3
     */
    fun substringsBetween(str: String?, open: String, close: String): Array<String>? {
        if (str == null || isEmpty(open) || isEmpty(close)) {
            return null
        }
        val strLen = str.length
        if (strLen == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY
        }
        val closeLen = close.length
        val openLen = open.length
        val list: MutableList<String> = ArrayList()
        var pos = 0
        while (pos < strLen - closeLen) {
            var start = str.indexOf(open, pos)
            if (start < 0) {
                break
            }
            start += openLen
            val end = str.indexOf(close, start)
            if (end < 0) {
                break
            }
            list.add(str.substring(start, end))
            pos = end + closeLen
        }
        return if (list.isEmpty()) {
            null
        } else list.toTypedArray()
    }

    /**
     *
     * Splits the provided text into an array, separator specified.
     * This is an alternative to using StringTokenizer.
     *
     *
     * The separator is not included in the returned String array.
     * Adjacent separators are treated as one separator.
     * For more control over the split use the StrTokenizer class.
     *
     *
     * A `null` input String returns `null`.
     *
     * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("a.b.c", '.')    = ["a", "b", "c"]
     * StringUtils.split("a..b.c", '.')   = ["a", "b", "c"]
     * StringUtils.split("a:b:c", '.')    = ["a:b:c"]
     * StringUtils.split("a b c", ' ')    = ["a", "b", "c"]
    </pre> *
     *
     * @param str  the String to parse, may be null
     * @param separatorChar  the character used as the delimiter
     * @return an array of parsed Strings, `null` if null String input
     * @since 2.0
     */
    fun split(str: String?, separatorChar: Char): Array<String>? {
        return splitWorker(str, separatorChar, false)
    }

    /**
     *
     * Splits the provided text into an array, separators specified.
     * This is an alternative to using StringTokenizer.
     *
     *
     * The separator is not included in the returned String array.
     * Adjacent separators are treated as one separator.
     * For more control over the split use the StrTokenizer class.
     *
     *
     * A `null` input String returns `null`.
     * A `null` separatorChars splits on whitespace.
     *
     * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("abc def", null) = ["abc", "def"]
     * StringUtils.split("abc def", " ")  = ["abc", "def"]
     * StringUtils.split("abc  def", " ") = ["abc", "def"]
     * StringUtils.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
    </pre> *
     *
     * @param str  the String to parse, may be null
     * @param separatorChars  the characters used as the delimiters,
     * `null` splits on whitespace
     * @return an array of parsed Strings, `null` if null String input
     */
    fun split(str: String?, separatorChars: String?): Array<String>? {
        return splitWorker(str, separatorChars, -1, false)
    }
    /**
     *
     * Splits the provided text into an array with a maximum length,
     * separators specified.
     *
     *
     * The separator is not included in the returned String array.
     * Adjacent separators are treated as one separator.
     *
     *
     * A `null` input String returns `null`.
     * A `null` separatorChars splits on whitespace.
     *
     *
     * If more than `max` delimited substrings are found, the last
     * returned string includes all characters after the first `max - 1`
     * returned strings (including separator characters).
     *
     * <pre>
     * StringUtils.split(null, *, *)            = null
     * StringUtils.split("", *, *)              = []
     * StringUtils.split("ab cd ef", null, 0)   = ["ab", "cd", "ef"]
     * StringUtils.split("ab   cd ef", null, 0) = ["ab", "cd", "ef"]
     * StringUtils.split("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
     * StringUtils.split("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
    </pre> *
     *
     * @param str  the String to parse, may be null
     * @param separatorChars  the characters used as the delimiters,
     * `null` splits on whitespace
     * @param max  the maximum number of elements to include in the
     * array. A zero or negative value implies no limit
     * @return an array of parsed Strings, `null` if null String input
     */
// Nested extraction
//-----------------------------------------------------------------------
// Splitting
//-----------------------------------------------------------------------
    /**
     *
     * Splits the provided text into an array, using whitespace as the
     * separator.
     * Whitespace is defined by [Character.isWhitespace].
     *
     *
     * The separator is not included in the returned String array.
     * Adjacent separators are treated as one separator.
     * For more control over the split use the StrTokenizer class.
     *
     *
     * A `null` input String returns `null`.
     *
     * <pre>
     * StringUtils.split(null)       = null
     * StringUtils.split("")         = []
     * StringUtils.split("abc def")  = ["abc", "def"]
     * StringUtils.split("abc  def") = ["abc", "def"]
     * StringUtils.split(" abc ")    = ["abc"]
    </pre> *
     *
     * @param str  the String to parse, may be null
     * @return an array of parsed Strings, `null` if null String input
     */
    @JvmOverloads
    fun split(str: String?, separatorChars: String? = null, max: Int = -1): Array<String>? {
        return splitWorker(str, separatorChars, max, false)
    }

    /**
     *
     * Splits the provided text into an array, separator string specified.
     *
     *
     * The separator(s) will not be included in the returned String array.
     * Adjacent separators are treated as one separator.
     *
     *
     * A `null` input String returns `null`.
     * A `null` separator splits on whitespace.
     *
     * <pre>
     * StringUtils.splitByWholeSeparator(null, *)               = null
     * StringUtils.splitByWholeSeparator("", *)                 = []
     * StringUtils.splitByWholeSeparator("ab de fg", null)      = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparator("ab   de fg", null)    = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparator("ab:cd:ef", ":")       = ["ab", "cd", "ef"]
     * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]
    </pre> *
     *
     * @param str  the String to parse, may be null
     * @param separator  String containing the String to be used as a delimiter,
     * `null` splits on whitespace
     * @return an array of parsed Strings, `null` if null String was input
     */
    fun splitByWholeSeparator(str: String?, separator: String?): Array<String>? {
        return splitByWholeSeparatorWorker(str, separator, -1, false)
    }

    /**
     *
     * Splits the provided text into an array, separator string specified.
     * Returns a maximum of `max` substrings.
     *
     *
     * The separator(s) will not be included in the returned String array.
     * Adjacent separators are treated as one separator.
     *
     *
     * A `null` input String returns `null`.
     * A `null` separator splits on whitespace.
     *
     * <pre>
     * StringUtils.splitByWholeSeparator(null, *, *)               = null
     * StringUtils.splitByWholeSeparator("", *, *)                 = []
     * StringUtils.splitByWholeSeparator("ab de fg", null, 0)      = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparator("ab   de fg", null, 0)    = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparator("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]
     * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 5) = ["ab", "cd", "ef"]
     * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 2) = ["ab", "cd-!-ef"]
    </pre> *
     *
     * @param str  the String to parse, may be null
     * @param separator  String containing the String to be used as a delimiter,
     * `null` splits on whitespace
     * @param max  the maximum number of elements to include in the returned
     * array. A zero or negative value implies no limit.
     * @return an array of parsed Strings, `null` if null String was input
     */
    fun splitByWholeSeparator(str: String?, separator: String?, max: Int): Array<String>? {
        return splitByWholeSeparatorWorker(str, separator, max, false)
    }

    /**
     *
     * Splits the provided text into an array, separator string specified.
     *
     *
     * The separator is not included in the returned String array.
     * Adjacent separators are treated as separators for empty tokens.
     * For more control over the split use the StrTokenizer class.
     *
     *
     * A `null` input String returns `null`.
     * A `null` separator splits on whitespace.
     *
     * <pre>
     * StringUtils.splitByWholeSeparatorPreserveAllTokens(null, *)               = null
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("", *)                 = []
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab de fg", null)      = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab   de fg", null)    = ["ab", "", "", "de", "fg"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab:cd:ef", ":")       = ["ab", "cd", "ef"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]
    </pre> *
     *
     * @param str  the String to parse, may be null
     * @param separator  String containing the String to be used as a delimiter,
     * `null` splits on whitespace
     * @return an array of parsed Strings, `null` if null String was input
     * @since 2.4
     */
    fun splitByWholeSeparatorPreserveAllTokens(str: String?, separator: String?): Array<String>? {
        return splitByWholeSeparatorWorker(str, separator, -1, true)
    }

    /**
     *
     * Splits the provided text into an array, separator string specified.
     * Returns a maximum of `max` substrings.
     *
     *
     * The separator is not included in the returned String array.
     * Adjacent separators are treated as separators for empty tokens.
     * For more control over the split use the StrTokenizer class.
     *
     *
     * A `null` input String returns `null`.
     * A `null` separator splits on whitespace.
     *
     * <pre>
     * StringUtils.splitByWholeSeparatorPreserveAllTokens(null, *, *)               = null
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("", *, *)                 = []
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab de fg", null, 0)      = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab   de fg", null, 0)    = ["ab", "", "", "de", "fg"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-", 5) = ["ab", "cd", "ef"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-", 2) = ["ab", "cd-!-ef"]
    </pre> *
     *
     * @param str  the String to parse, may be null
     * @param separator  String containing the String to be used as a delimiter,
     * `null` splits on whitespace
     * @param max  the maximum number of elements to include in the returned
     * array. A zero or negative value implies no limit.
     * @return an array of parsed Strings, `null` if null String was input
     * @since 2.4
     */
    fun splitByWholeSeparatorPreserveAllTokens(str: String?, separator: String?, max: Int): Array<String>? {
        return splitByWholeSeparatorWorker(str, separator, max, true)
    }

    /**
     * Performs the logic for the `splitByWholeSeparatorPreserveAllTokens` methods.
     *
     * @param str  the String to parse, may be `null`
     * @param separator  String containing the String to be used as a delimiter,
     * `null` splits on whitespace
     * @param max  the maximum number of elements to include in the returned
     * array. A zero or negative value implies no limit.
     * @param preserveAllTokens if `true`, adjacent separators are
     * treated as empty token separators; if `false`, adjacent
     * separators are treated as one separator.
     * @return an array of parsed Strings, `null` if null String input
     * @since 2.4
     */
    private fun splitByWholeSeparatorWorker(
        str: String?, separator: String?, max: Int, preserveAllTokens: Boolean
    ): Array<String>? {
        if (str == null) {
            return null
        }
        val len = str.length
        if (len == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY
        }
        if (separator == null || EMPTY == separator) { // Split on whitespace.
            return splitWorker(str, null, max, preserveAllTokens)
        }
        val separatorLength = separator.length
        val substrings = ArrayList<String>()
        var numberOfSubstrings = 0
        var beg = 0
        var end = 0
        while (end < len) {
            end = str.indexOf(separator, beg)
            if (end > -1) {
                if (end > beg) {
                    numberOfSubstrings += 1
                    if (numberOfSubstrings == max) {
                        end = len
                        substrings.add(str.substring(beg))
                    } else { // The following is OK, because String.substring( beg, end ) excludes
// the character at the position 'end'.
                        substrings.add(str.substring(beg, end))
                        // Set the starting point for the next search.
// The following is equivalent to beg = end + (separatorLength - 1) + 1,
// which is the right calculation:
                        beg = end + separatorLength
                    }
                } else { // We found a consecutive occurrence of the separator, so skip it.
                    if (preserveAllTokens) {
                        numberOfSubstrings += 1
                        if (numberOfSubstrings == max) {
                            end = len
                            substrings.add(str.substring(beg))
                        } else {
                            substrings.add(EMPTY)
                        }
                    }
                    beg = end + separatorLength
                }
            } else { // String.substring( beg ) goes from 'beg' to the end of the String.
                substrings.add(str.substring(beg))
                end = len
            }
        }
        return substrings.toTypedArray()
    }
    // -----------------------------------------------------------------------
    /**
     *
     * Splits the provided text into an array, using whitespace as the
     * separator, preserving all tokens, including empty tokens created by
     * adjacent separators. This is an alternative to using StringTokenizer.
     * Whitespace is defined by [Character.isWhitespace].
     *
     *
     * The separator is not included in the returned String array.
     * Adjacent separators are treated as separators for empty tokens.
     * For more control over the split use the StrTokenizer class.
     *
     *
     * A `null` input String returns `null`.
     *
     * <pre>
     * StringUtils.splitPreserveAllTokens(null)       = null
     * StringUtils.splitPreserveAllTokens("")         = []
     * StringUtils.splitPreserveAllTokens("abc def")  = ["abc", "def"]
     * StringUtils.splitPreserveAllTokens("abc  def") = ["abc", "", "def"]
     * StringUtils.splitPreserveAllTokens(" abc ")    = ["", "abc", ""]
    </pre> *
     *
     * @param str  the String to parse, may be `null`
     * @return an array of parsed Strings, `null` if null String input
     * @since 2.1
     */
    fun splitPreserveAllTokens(str: String?): Array<String>? {
        return splitWorker(str, null, -1, true)
    }

    /**
     *
     * Splits the provided text into an array, separator specified,
     * preserving all tokens, including empty tokens created by adjacent
     * separators. This is an alternative to using StringTokenizer.
     *
     *
     * The separator is not included in the returned String array.
     * Adjacent separators are treated as separators for empty tokens.
     * For more control over the split use the StrTokenizer class.
     *
     *
     * A `null` input String returns `null`.
     *
     * <pre>
     * StringUtils.splitPreserveAllTokens(null, *)         = null
     * StringUtils.splitPreserveAllTokens("", *)           = []
     * StringUtils.splitPreserveAllTokens("a.b.c", '.')    = ["a", "b", "c"]
     * StringUtils.splitPreserveAllTokens("a..b.c", '.')   = ["a", "", "b", "c"]
     * StringUtils.splitPreserveAllTokens("a:b:c", '.')    = ["a:b:c"]
     * StringUtils.splitPreserveAllTokens("a\tb\nc", null) = ["a", "b", "c"]
     * StringUtils.splitPreserveAllTokens("a b c", ' ')    = ["a", "b", "c"]
     * StringUtils.splitPreserveAllTokens("a b c ", ' ')   = ["a", "b", "c", ""]
     * StringUtils.splitPreserveAllTokens("a b c  ", ' ')   = ["a", "b", "c", "", ""]
     * StringUtils.splitPreserveAllTokens(" a b c", ' ')   = ["", a", "b", "c"]
     * StringUtils.splitPreserveAllTokens("  a b c", ' ')  = ["", "", a", "b", "c"]
     * StringUtils.splitPreserveAllTokens(" a b c ", ' ')  = ["", a", "b", "c", ""]
    </pre> *
     *
     * @param str  the String to parse, may be `null`
     * @param separatorChar  the character used as the delimiter,
     * `null` splits on whitespace
     * @return an array of parsed Strings, `null` if null String input
     * @since 2.1
     */
    fun splitPreserveAllTokens(str: String?, separatorChar: Char): Array<String>? {
        return splitWorker(str, separatorChar, true)
    }

    /**
     * Performs the logic for the `split` and
     * `splitPreserveAllTokens` methods that do not return a
     * maximum array length.
     *
     * @param str  the String to parse, may be `null`
     * @param separatorChar the separate character
     * @param preserveAllTokens if `true`, adjacent separators are
     * treated as empty token separators; if `false`, adjacent
     * separators are treated as one separator.
     * @return an array of parsed Strings, `null` if null String input
     */
    private fun splitWorker(
        str: String?,
        separatorChar: Char,
        preserveAllTokens: Boolean
    ): Array<String>? { // Performance tuned for 2.0 (JDK1.4)
        if (str == null) {
            return null
        }
        val len = str.length
        if (len == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY
        }
        val list: MutableList<String> = ArrayList()
        var i = 0
        var start = 0
        var match = false
        var lastMatch = false
        while (i < len) {
            if (str[i] == separatorChar) {
                if (match || preserveAllTokens) {
                    list.add(str.substring(start, i))
                    match = false
                    lastMatch = true
                }
                start = ++i
                continue
            }
            lastMatch = false
            match = true
            i++
        }
        if (match || preserveAllTokens && lastMatch) {
            list.add(str.substring(start, i))
        }
        return list.toTypedArray()
    }

    /**
     *
     * Splits the provided text into an array, separators specified,
     * preserving all tokens, including empty tokens created by adjacent
     * separators. This is an alternative to using StringTokenizer.
     *
     *
     * The separator is not included in the returned String array.
     * Adjacent separators are treated as separators for empty tokens.
     * For more control over the split use the StrTokenizer class.
     *
     *
     * A `null` input String returns `null`.
     * A `null` separatorChars splits on whitespace.
     *
     * <pre>
     * StringUtils.splitPreserveAllTokens(null, *)           = null
     * StringUtils.splitPreserveAllTokens("", *)             = []
     * StringUtils.splitPreserveAllTokens("abc def", null)   = ["abc", "def"]
     * StringUtils.splitPreserveAllTokens("abc def", " ")    = ["abc", "def"]
     * StringUtils.splitPreserveAllTokens("abc  def", " ")   = ["abc", "", def"]
     * StringUtils.splitPreserveAllTokens("ab:cd:ef", ":")   = ["ab", "cd", "ef"]
     * StringUtils.splitPreserveAllTokens("ab:cd:ef:", ":")  = ["ab", "cd", "ef", ""]
     * StringUtils.splitPreserveAllTokens("ab:cd:ef::", ":") = ["ab", "cd", "ef", "", ""]
     * StringUtils.splitPreserveAllTokens("ab::cd:ef", ":")  = ["ab", "", cd", "ef"]
     * StringUtils.splitPreserveAllTokens(":cd:ef", ":")     = ["", cd", "ef"]
     * StringUtils.splitPreserveAllTokens("::cd:ef", ":")    = ["", "", cd", "ef"]
     * StringUtils.splitPreserveAllTokens(":cd:ef:", ":")    = ["", cd", "ef", ""]
    </pre> *
     *
     * @param str  the String to parse, may be `null`
     * @param separatorChars  the characters used as the delimiters,
     * `null` splits on whitespace
     * @return an array of parsed Strings, `null` if null String input
     * @since 2.1
     */
    fun splitPreserveAllTokens(str: String?, separatorChars: String?): Array<String>? {
        return splitWorker(str, separatorChars, -1, true)
    }

    /**
     *
     * Splits the provided text into an array with a maximum length,
     * separators specified, preserving all tokens, including empty tokens
     * created by adjacent separators.
     *
     *
     * The separator is not included in the returned String array.
     * Adjacent separators are treated as separators for empty tokens.
     * Adjacent separators are treated as one separator.
     *
     *
     * A `null` input String returns `null`.
     * A `null` separatorChars splits on whitespace.
     *
     *
     * If more than `max` delimited substrings are found, the last
     * returned string includes all characters after the first `max - 1`
     * returned strings (including separator characters).
     *
     * <pre>
     * StringUtils.splitPreserveAllTokens(null, *, *)            = null
     * StringUtils.splitPreserveAllTokens("", *, *)              = []
     * StringUtils.splitPreserveAllTokens("ab de fg", null, 0)   = ["ab", "cd", "ef"]
     * StringUtils.splitPreserveAllTokens("ab   de fg", null, 0) = ["ab", "cd", "ef"]
     * StringUtils.splitPreserveAllTokens("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
     * StringUtils.splitPreserveAllTokens("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
     * StringUtils.splitPreserveAllTokens("ab   de fg", null, 2) = ["ab", "  de fg"]
     * StringUtils.splitPreserveAllTokens("ab   de fg", null, 3) = ["ab", "", " de fg"]
     * StringUtils.splitPreserveAllTokens("ab   de fg", null, 4) = ["ab", "", "", "de fg"]
    </pre> *
     *
     * @param str  the String to parse, may be `null`
     * @param separatorChars  the characters used as the delimiters,
     * `null` splits on whitespace
     * @param max  the maximum number of elements to include in the
     * array. A zero or negative value implies no limit
     * @return an array of parsed Strings, `null` if null String input
     * @since 2.1
     */
    fun splitPreserveAllTokens(str: String?, separatorChars: String?, max: Int): Array<String>? {
        return splitWorker(str, separatorChars, max, true)
    }

    /**
     * Performs the logic for the `split` and
     * `splitPreserveAllTokens` methods that return a maximum array
     * length.
     *
     * @param str  the String to parse, may be `null`
     * @param separatorChars the separate character
     * @param max  the maximum number of elements to include in the
     * array. A zero or negative value implies no limit.
     * @param preserveAllTokens if `true`, adjacent separators are
     * treated as empty token separators; if `false`, adjacent
     * separators are treated as one separator.
     * @return an array of parsed Strings, `null` if null String input
     */
    private fun splitWorker(
        str: String?,
        separatorChars: String?,
        max: Int,
        preserveAllTokens: Boolean
    ): Array<String>? { // Performance tuned for 2.0 (JDK1.4)
// Direct code is quicker than StringTokenizer.
// Also, StringTokenizer uses isSpace() not isWhitespace()
        if (str == null) {
            return null
        }
        val len = str.length
        if (len == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY
        }
        val list: MutableList<String> = ArrayList()
        var sizePlus1 = 1
        var i = 0
        var start = 0
        var match = false
        var lastMatch = false
        if (separatorChars == null) { // Null separator means use whitespace
            while (i < len) {
                if (Character.isWhitespace(str[i])) {
                    if (match || preserveAllTokens) {
                        lastMatch = true
                        if (sizePlus1++ == max) {
                            i = len
                            lastMatch = false
                        }
                        list.add(str.substring(start, i))
                        match = false
                    }
                    start = ++i
                    continue
                }
                lastMatch = false
                match = true
                i++
            }
        } else if (separatorChars.length == 1) { // Optimise 1 character case
            val sep = separatorChars[0]
            while (i < len) {
                if (str[i] == sep) {
                    if (match || preserveAllTokens) {
                        lastMatch = true
                        if (sizePlus1++ == max) {
                            i = len
                            lastMatch = false
                        }
                        list.add(str.substring(start, i))
                        match = false
                    }
                    start = ++i
                    continue
                }
                lastMatch = false
                match = true
                i++
            }
        } else { // standard case
            while (i < len) {
                if (separatorChars.indexOf(str[i]) >= 0) {
                    if (match || preserveAllTokens) {
                        lastMatch = true
                        if (sizePlus1++ == max) {
                            i = len
                            lastMatch = false
                        }
                        list.add(str.substring(start, i))
                        match = false
                    }
                    start = ++i
                    continue
                }
                lastMatch = false
                match = true
                i++
            }
        }
        if (match || preserveAllTokens && lastMatch) {
            list.add(str.substring(start, i))
        }
        return list.toTypedArray()
    }

    /**
     *
     * Splits a String by Character type as returned by
     * `java.lang.Character.getType(char)`. Groups of contiguous
     * characters of the same type are returned as complete tokens.
     * <pre>
     * StringUtils.splitByCharacterType(null)         = null
     * StringUtils.splitByCharacterType("")           = []
     * StringUtils.splitByCharacterType("ab de fg")   = ["ab", " ", "de", " ", "fg"]
     * StringUtils.splitByCharacterType("ab   de fg") = ["ab", "   ", "de", " ", "fg"]
     * StringUtils.splitByCharacterType("ab:cd:ef")   = ["ab", ":", "cd", ":", "ef"]
     * StringUtils.splitByCharacterType("number5")    = ["number", "5"]
     * StringUtils.splitByCharacterType("fooBar")     = ["foo", "B", "ar"]
     * StringUtils.splitByCharacterType("foo200Bar")  = ["foo", "200", "B", "ar"]
     * StringUtils.splitByCharacterType("ASFRules")   = ["ASFR", "ules"]
    </pre> *
     * @param str the String to split, may be `null`
     * @return an array of parsed Strings, `null` if null String input
     * @since 2.4
     */
    fun splitByCharacterType(str: String?): Array<String>? {
        return splitByCharacterType(str, false)
    }

    /**
     *
     * Splits a String by Character type as returned by
     * `java.lang.Character.getType(char)`. Groups of contiguous
     * characters of the same type are returned as complete tokens, with the
     * following exception: the character of type
     * `Character.UPPERCASE_LETTER`, if any, immediately
     * preceding a token of type `Character.LOWERCASE_LETTER`
     * will belong to the following token rather than to the preceding, if any,
     * `Character.UPPERCASE_LETTER` token.
     * <pre>
     * StringUtils.splitByCharacterTypeCamelCase(null)         = null
     * StringUtils.splitByCharacterTypeCamelCase("")           = []
     * StringUtils.splitByCharacterTypeCamelCase("ab de fg")   = ["ab", " ", "de", " ", "fg"]
     * StringUtils.splitByCharacterTypeCamelCase("ab   de fg") = ["ab", "   ", "de", " ", "fg"]
     * StringUtils.splitByCharacterTypeCamelCase("ab:cd:ef")   = ["ab", ":", "cd", ":", "ef"]
     * StringUtils.splitByCharacterTypeCamelCase("number5")    = ["number", "5"]
     * StringUtils.splitByCharacterTypeCamelCase("fooBar")     = ["foo", "Bar"]
     * StringUtils.splitByCharacterTypeCamelCase("foo200Bar")  = ["foo", "200", "Bar"]
     * StringUtils.splitByCharacterTypeCamelCase("ASFRules")   = ["ASF", "Rules"]
    </pre> *
     * @param str the String to split, may be `null`
     * @return an array of parsed Strings, `null` if null String input
     * @since 2.4
     */
    fun splitByCharacterTypeCamelCase(str: String?): Array<String>? {
        return splitByCharacterType(str, true)
    }

    /**
     *
     * Splits a String by Character type as returned by
     * `java.lang.Character.getType(char)`. Groups of contiguous
     * characters of the same type are returned as complete tokens, with the
     * following exception: if `camelCase` is `true`,
     * the character of type `Character.UPPERCASE_LETTER`, if any,
     * immediately preceding a token of type `Character.LOWERCASE_LETTER`
     * will belong to the following token rather than to the preceding, if any,
     * `Character.UPPERCASE_LETTER` token.
     * @param str the String to split, may be `null`
     * @param camelCase whether to use so-called "camel-case" for letter types
     * @return an array of parsed Strings, `null` if null String input
     * @since 2.4
     */
    private fun splitByCharacterType(str: String?, camelCase: Boolean): Array<String>? {
        if (str == null) {
            return null
        }
        if (str.isEmpty()) {
            return ArrayUtils.EMPTY_STRING_ARRAY
        }
        val c = str.toCharArray()
        val list: MutableList<String> = ArrayList()
        var tokenStart = 0
        var currentType = Character.getType(c[tokenStart])
        for (pos in tokenStart + 1 until c.size) {
            val type = Character.getType(c[pos])
            if (type == currentType) {
                continue
            }
            if (camelCase && type == Character.LOWERCASE_LETTER.toInt() && currentType == Character.UPPERCASE_LETTER.toInt()) {
                val newTokenStart = pos - 1
                if (newTokenStart != tokenStart) {
                    list.add(String(c, tokenStart, newTokenStart - tokenStart))
                    tokenStart = newTokenStart
                }
            } else {
                list.add(String(c, tokenStart, pos - tokenStart))
                tokenStart = pos
            }
            currentType = type
        }
        list.add(String(c, tokenStart, c.size - tokenStart))
        return list.toTypedArray()
    }
    // Joining
//-----------------------------------------------------------------------
    /**
     *
     * Joins the elements of the provided array into a single String
     * containing the provided list of elements.
     *
     *
     * No separator is added to the joined String.
     * Null objects or empty strings within the array are represented by
     * empty strings.
     *
     * <pre>
     * StringUtils.join(null)            = null
     * StringUtils.join([])              = ""
     * StringUtils.join([null])          = ""
     * StringUtils.join(["a", "b", "c"]) = "abc"
     * StringUtils.join([null, "", "a"]) = "a"
    </pre> *
     *
     * @param <T> the specific type of values to join together
     * @param elements  the values to join together, may be null
     * @return the joined String, `null` if null array input
     * @since 2.0
     * @since 3.0 Changed signature to use varargs
    </T> */
    @SafeVarargs
    fun <T> join(vararg elements: T): String {
        return join(elements, null)
    }

    /**
     *
     * Joins the elements of the provided array into a single String
     * containing the provided list of elements.
     *
     *
     * No delimiter is added before or after the list.
     * Null objects or empty strings within the array are represented by
     * empty strings.
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringUtils.join(["a", "b", "c"], null) = "abc"
     * StringUtils.join([null, "", "a"], ';')  = ";;a"
    </pre> *
     *
     * @param array  the array of values to join together, may be null
     * @param separator  the separator character to use
     * @return the joined String, `null` if null array input
     * @since 2.0
     */
    fun join(array: Array<Any?>?, separator: Char): String? {
        return if (array == null) {
            null
        } else join(array, separator, 0, array.size)
    }

    /**
     *
     *
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     *
     *
     *
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     *
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
    </pre> *
     *
     * @param array
     * the array of values to join together, may be null
     * @param separator
     * the separator character to use
     * @return the joined String, `null` if null array input
     * @since 3.2
     */
    fun join(array: LongArray?, separator: Char): String? {
        return if (array == null) {
            null
        } else join(array, separator, 0, array.size)
    }

    /**
     *
     *
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     *
     *
     *
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     *
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
    </pre> *
     *
     * @param array
     * the array of values to join together, may be null
     * @param separator
     * the separator character to use
     * @return the joined String, `null` if null array input
     * @since 3.2
     */
    fun join(array: IntArray?, separator: Char): String? {
        return if (array == null) {
            null
        } else join(array, separator, 0, array.size)
    }

    /**
     *
     *
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     *
     *
     *
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     *
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
    </pre> *
     *
     * @param array
     * the array of values to join together, may be null
     * @param separator
     * the separator character to use
     * @return the joined String, `null` if null array input
     * @since 3.2
     */
    fun join(array: ShortArray?, separator: Char): String? {
        return if (array == null) {
            null
        } else join(array, separator, 0, array.size)
    }

    /**
     *
     *
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     *
     *
     *
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     *
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
    </pre> *
     *
     * @param array
     * the array of values to join together, may be null
     * @param separator
     * the separator character to use
     * @return the joined String, `null` if null array input
     * @since 3.2
     */
    fun join(array: ByteArray?, separator: Char): String? {
        return if (array == null) {
            null
        } else join(array, separator, 0, array.size)
    }

    /**
     *
     *
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     *
     *
     *
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     *
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
    </pre> *
     *
     * @param array
     * the array of values to join together, may be null
     * @param separator
     * the separator character to use
     * @return the joined String, `null` if null array input
     * @since 3.2
     */
    fun join(array: CharArray?, separator: Char): String? {
        return if (array == null) {
            null
        } else join(array, separator, 0, array.size)
    }

    /**
     *
     *
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     *
     *
     *
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     *
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
    </pre> *
     *
     * @param array
     * the array of values to join together, may be null
     * @param separator
     * the separator character to use
     * @return the joined String, `null` if null array input
     * @since 3.2
     */
    fun join(array: FloatArray?, separator: Char): String? {
        return if (array == null) {
            null
        } else join(array, separator, 0, array.size)
    }

    /**
     *
     *
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     *
     *
     *
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     *
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
    </pre> *
     *
     * @param array
     * the array of values to join together, may be null
     * @param separator
     * the separator character to use
     * @return the joined String, `null` if null array input
     * @since 3.2
     */
    fun join(array: DoubleArray?, separator: Char): String? {
        return if (array == null) {
            null
        } else join(array, separator, 0, array.size)
    }

    /**
     *
     * Joins the elements of the provided array into a single String
     * containing the provided list of elements.
     *
     *
     * No delimiter is added before or after the list.
     * Null objects or empty strings within the array are represented by
     * empty strings.
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringUtils.join(["a", "b", "c"], null) = "abc"
     * StringUtils.join([null, "", "a"], ';')  = ";;a"
    </pre> *
     *
     * @param array  the array of values to join together, may be null
     * @param separator  the separator character to use
     * @param startIndex the first index to start joining from.  It is
     * an error to pass in a start index past the end of the array
     * @param endIndex the index to stop joining from (exclusive). It is
     * an error to pass in an end index past the end of the array
     * @return the joined String, `null` if null array input
     * @since 2.0
     */
    fun join(array: Array<Any?>?, separator: Char, startIndex: Int, endIndex: Int): String? {
        if (array == null) {
            return null
        }
        val noOfItems = endIndex - startIndex
        if (noOfItems <= 0) {
            return EMPTY
        }
        val buf = newStringBuilder(noOfItems)
        for (i in startIndex until endIndex) {
            if (i > startIndex) {
                buf.append(separator)
            }
            if (array[i] != null) {
                buf.append(array[i])
            }
        }
        return buf.toString()
    }

    /**
     *
     *
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     *
     *
     *
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     *
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
    </pre> *
     *
     * @param array
     * the array of values to join together, may be null
     * @param separator
     * the separator character to use
     * @param startIndex
     * the first index to start joining from. It is an error to pass in a start index past the end of the
     * array
     * @param endIndex
     * the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     * the array
     * @return the joined String, `null` if null array input
     * @since 3.2
     */
    fun join(array: LongArray?, separator: Char, startIndex: Int, endIndex: Int): String? {
        if (array == null) {
            return null
        }
        val noOfItems = endIndex - startIndex
        if (noOfItems <= 0) {
            return EMPTY
        }
        val buf = newStringBuilder(noOfItems)
        for (i in startIndex until endIndex) {
            if (i > startIndex) {
                buf.append(separator)
            }
            buf.append(array[i])
        }
        return buf.toString()
    }

    /**
     *
     *
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     *
     *
     *
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     *
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
    </pre> *
     *
     * @param array
     * the array of values to join together, may be null
     * @param separator
     * the separator character to use
     * @param startIndex
     * the first index to start joining from. It is an error to pass in a start index past the end of the
     * array
     * @param endIndex
     * the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     * the array
     * @return the joined String, `null` if null array input
     * @since 3.2
     */
    fun join(array: IntArray?, separator: Char, startIndex: Int, endIndex: Int): String? {
        if (array == null) {
            return null
        }
        val noOfItems = endIndex - startIndex
        if (noOfItems <= 0) {
            return EMPTY
        }
        val buf = newStringBuilder(noOfItems)
        for (i in startIndex until endIndex) {
            if (i > startIndex) {
                buf.append(separator)
            }
            buf.append(array[i])
        }
        return buf.toString()
    }

    /**
     *
     *
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     *
     *
     *
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     *
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
    </pre> *
     *
     * @param array
     * the array of values to join together, may be null
     * @param separator
     * the separator character to use
     * @param startIndex
     * the first index to start joining from. It is an error to pass in a start index past the end of the
     * array
     * @param endIndex
     * the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     * the array
     * @return the joined String, `null` if null array input
     * @since 3.2
     */
    fun join(array: ByteArray?, separator: Char, startIndex: Int, endIndex: Int): String? {
        if (array == null) {
            return null
        }
        val noOfItems = endIndex - startIndex
        if (noOfItems <= 0) {
            return EMPTY
        }
        val buf = newStringBuilder(noOfItems)
        for (i in startIndex until endIndex) {
            if (i > startIndex) {
                buf.append(separator)
            }
            buf.append(array[i])
        }
        return buf.toString()
    }

    /**
     *
     *
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     *
     *
     *
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     *
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
    </pre> *
     *
     * @param array
     * the array of values to join together, may be null
     * @param separator
     * the separator character to use
     * @param startIndex
     * the first index to start joining from. It is an error to pass in a start index past the end of the
     * array
     * @param endIndex
     * the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     * the array
     * @return the joined String, `null` if null array input
     * @since 3.2
     */
    fun join(array: ShortArray?, separator: Char, startIndex: Int, endIndex: Int): String? {
        if (array == null) {
            return null
        }
        val noOfItems = endIndex - startIndex
        if (noOfItems <= 0) {
            return EMPTY
        }
        val buf = newStringBuilder(noOfItems)
        for (i in startIndex until endIndex) {
            if (i > startIndex) {
                buf.append(separator)
            }
            buf.append(array[i])
        }
        return buf.toString()
    }

    /**
     *
     *
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     *
     *
     *
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     *
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
    </pre> *
     *
     * @param array
     * the array of values to join together, may be null
     * @param separator
     * the separator character to use
     * @param startIndex
     * the first index to start joining from. It is an error to pass in a start index past the end of the
     * array
     * @param endIndex
     * the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     * the array
     * @return the joined String, `null` if null array input
     * @since 3.2
     */
    fun join(array: CharArray?, separator: Char, startIndex: Int, endIndex: Int): String? {
        if (array == null) {
            return null
        }
        val noOfItems = endIndex - startIndex
        if (noOfItems <= 0) {
            return EMPTY
        }
        val buf = newStringBuilder(noOfItems)
        for (i in startIndex until endIndex) {
            if (i > startIndex) {
                buf.append(separator)
            }
            buf.append(array[i])
        }
        return buf.toString()
    }

    /**
     *
     *
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     *
     *
     *
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     *
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
    </pre> *
     *
     * @param array
     * the array of values to join together, may be null
     * @param separator
     * the separator character to use
     * @param startIndex
     * the first index to start joining from. It is an error to pass in a start index past the end of the
     * array
     * @param endIndex
     * the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     * the array
     * @return the joined String, `null` if null array input
     * @since 3.2
     */
    fun join(array: DoubleArray?, separator: Char, startIndex: Int, endIndex: Int): String? {
        if (array == null) {
            return null
        }
        val noOfItems = endIndex - startIndex
        if (noOfItems <= 0) {
            return EMPTY
        }
        val buf = newStringBuilder(noOfItems)
        for (i in startIndex until endIndex) {
            if (i > startIndex) {
                buf.append(separator)
            }
            buf.append(array[i])
        }
        return buf.toString()
    }

    /**
     *
     *
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     *
     *
     *
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     *
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
    </pre> *
     *
     * @param array
     * the array of values to join together, may be null
     * @param separator
     * the separator character to use
     * @param startIndex
     * the first index to start joining from. It is an error to pass in a start index past the end of the
     * array
     * @param endIndex
     * the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     * the array
     * @return the joined String, `null` if null array input
     * @since 3.2
     */
    fun join(array: FloatArray?, separator: Char, startIndex: Int, endIndex: Int): String? {
        if (array == null) {
            return null
        }
        val noOfItems = endIndex - startIndex
        if (noOfItems <= 0) {
            return EMPTY
        }
        val buf = newStringBuilder(noOfItems)
        for (i in startIndex until endIndex) {
            if (i > startIndex) {
                buf.append(separator)
            }
            buf.append(array[i])
        }
        return buf.toString()
    }

    /**
     *
     * Joins the elements of the provided array into a single String
     * containing the provided list of elements.
     *
     *
     * No delimiter is added before or after the list.
     * A `null` separator is the same as an empty String ("").
     * Null objects or empty strings within the array are represented by
     * empty strings.
     *
     * <pre>
     * StringUtils.join(null, *)                = null
     * StringUtils.join([], *)                  = ""
     * StringUtils.join([null], *)              = ""
     * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], null)  = "abc"
     * StringUtils.join(["a", "b", "c"], "")    = "abc"
     * StringUtils.join([null, "", "a"], ',')   = ",,a"
    </pre> *
     *
     * @param array  the array of values to join together, may be null
     * @param separator  the separator character to use, null treated as ""
     * @return the joined String, `null` if null array input
     */
    fun join(array: Array<Any?>?, separator: String?): String? {
        return if (array == null) {
            null
        } else join(array, separator, 0, array.size)
    }

    /**
     *
     * Joins the elements of the provided array into a single String
     * containing the provided list of elements.
     *
     *
     * No delimiter is added before or after the list.
     * A `null` separator is the same as an empty String ("").
     * Null objects or empty strings within the array are represented by
     * empty strings.
     *
     * <pre>
     * StringUtils.join(null, *, *, *)                = null
     * StringUtils.join([], *, *, *)                  = ""
     * StringUtils.join([null], *, *, *)              = ""
     * StringUtils.join(["a", "b", "c"], "--", 0, 3)  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], "--", 1, 3)  = "b--c"
     * StringUtils.join(["a", "b", "c"], "--", 2, 3)  = "c"
     * StringUtils.join(["a", "b", "c"], "--", 2, 2)  = ""
     * StringUtils.join(["a", "b", "c"], null, 0, 3)  = "abc"
     * StringUtils.join(["a", "b", "c"], "", 0, 3)    = "abc"
     * StringUtils.join([null, "", "a"], ',', 0, 3)   = ",,a"
    </pre> *
     *
     * @param array  the array of values to join together, may be null
     * @param separator  the separator character to use, null treated as ""
     * @param startIndex the first index to start joining from.
     * @param endIndex the index to stop joining from (exclusive).
     * @return the joined String, `null` if null array input; or the empty string
     * if `endIndex - startIndex <= 0`. The number of joined entries is given by
     * `endIndex - startIndex`
     * @throws ArrayIndexOutOfBoundsException ife<br></br>
     * `startIndex < 0` or <br></br>
     * `startIndex >= array.length()` or <br></br>
     * `endIndex < 0` or <br></br>
     * `endIndex > array.length()`
     */
    fun join(array: Array<Any?>?, separator: String?, startIndex: Int, endIndex: Int): String? {
        var separator = separator
        if (array == null) {
            return null
        }
        if (separator == null) {
            separator = EMPTY
        }
        // endIndex - startIndex > 0:   Len = NofStrings *(len(firstString) + len(separator))
//           (Assuming that all Strings are roughly equally long)
        val noOfItems = endIndex - startIndex
        if (noOfItems <= 0) {
            return EMPTY
        }
        val buf = newStringBuilder(noOfItems)
        for (i in startIndex until endIndex) {
            if (i > startIndex) {
                buf.append(separator)
            }
            if (array[i] != null) {
                buf.append(array[i])
            }
        }
        return buf.toString()
    }

    /**
     *
     * Joins the elements of the provided `Iterator` into
     * a single String containing the provided elements.
     *
     *
     * No delimiter is added before or after the list. Null objects or empty
     * strings within the iteration are represented by empty strings.
     *
     *
     * See the examples here: [.join].
     *
     * @param iterator  the `Iterator` of values to join together, may be null
     * @param separator  the separator character to use
     * @return the joined String, `null` if null iterator input
     * @since 2.0
     */
    fun join(
        iterator: Iterator<*>?,
        separator: Char
    ): String? { // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null
        }
        if (!iterator.hasNext()) {
            return EMPTY
        }
        val first = iterator.next()
        if (!iterator.hasNext()) {
            return Objects.toString(first, EMPTY)
        }
        // two or more elements
        val buf =
            StringBuilder(STRING_BUILDER_SIZE) // Java default is 16, probably too small
        if (first != null) {
            buf.append(first)
        }
        while (iterator.hasNext()) {
            buf.append(separator)
            val obj = iterator.next()
            if (obj != null) {
                buf.append(obj)
            }
        }
        return buf.toString()
    }

    /**
     *
     * Joins the elements of the provided `Iterator` into
     * a single String containing the provided elements.
     *
     *
     * No delimiter is added before or after the list.
     * A `null` separator is the same as an empty String ("").
     *
     *
     * See the examples here: [.join].
     *
     * @param iterator  the `Iterator` of values to join together, may be null
     * @param separator  the separator character to use, null treated as ""
     * @return the joined String, `null` if null iterator input
     */
    fun join(
        iterator: Iterator<*>?,
        separator: String?
    ): String? { // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null
        }
        if (!iterator.hasNext()) {
            return EMPTY
        }
        val first = iterator.next()
        if (!iterator.hasNext()) {
            return Objects.toString(first, "")
        }
        // two or more elements
        val buf =
            StringBuilder(STRING_BUILDER_SIZE) // Java default is 16, probably too small
        if (first != null) {
            buf.append(first)
        }
        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator)
            }
            val obj = iterator.next()
            if (obj != null) {
                buf.append(obj)
            }
        }
        return buf.toString()
    }

    /**
     *
     * Joins the elements of the provided `Iterable` into
     * a single String containing the provided elements.
     *
     *
     * No delimiter is added before or after the list. Null objects or empty
     * strings within the iteration are represented by empty strings.
     *
     *
     * See the examples here: [.join].
     *
     * @param iterable  the `Iterable` providing the values to join together, may be null
     * @param separator  the separator character to use
     * @return the joined String, `null` if null iterator input
     * @since 2.3
     */
    fun join(iterable: Iterable<*>?, separator: Char): String? {
        return if (iterable == null) {
            null
        } else join(iterable.iterator(), separator)
    }

    /**
     *
     * Joins the elements of the provided `Iterable` into
     * a single String containing the provided elements.
     *
     *
     * No delimiter is added before or after the list.
     * A `null` separator is the same as an empty String ("").
     *
     *
     * See the examples here: [.join].
     *
     * @param iterable  the `Iterable` providing the values to join together, may be null
     * @param separator  the separator character to use, null treated as ""
     * @return the joined String, `null` if null iterator input
     * @since 2.3
     */
    fun join(iterable: Iterable<*>?, separator: String?): String? {
        return if (iterable == null) {
            null
        } else join(iterable.iterator(), separator)
    }

    /**
     *
     * Joins the elements of the provided `List` into a single String
     * containing the provided list of elements.
     *
     *
     * No delimiter is added before or after the list.
     * Null objects or empty strings within the array are represented by
     * empty strings.
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringUtils.join(["a", "b", "c"], null) = "abc"
     * StringUtils.join([null, "", "a"], ';')  = ";;a"
    </pre> *
     *
     * @param list  the `List` of values to join together, may be null
     * @param separator  the separator character to use
     * @param startIndex the first index to start joining from.  It is
     * an error to pass in a start index past the end of the list
     * @param endIndex the index to stop joining from (exclusive). It is
     * an error to pass in an end index past the end of the list
     * @return the joined String, `null` if null list input
     * @since 3.8
     */
    fun join(list: List<*>?, separator: Char, startIndex: Int, endIndex: Int): String? {
        if (list == null) {
            return null
        }
        val noOfItems = endIndex - startIndex
        if (noOfItems <= 0) {
            return EMPTY
        }
        val subList = list.subList(startIndex, endIndex)
        return join(subList.iterator(), separator)
    }

    /**
     *
     * Joins the elements of the provided `List` into a single String
     * containing the provided list of elements.
     *
     *
     * No delimiter is added before or after the list.
     * Null objects or empty strings within the array are represented by
     * empty strings.
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringUtils.join(["a", "b", "c"], null) = "abc"
     * StringUtils.join([null, "", "a"], ';')  = ";;a"
    </pre> *
     *
     * @param list  the `List` of values to join together, may be null
     * @param separator  the separator character to use
     * @param startIndex the first index to start joining from.  It is
     * an error to pass in a start index past the end of the list
     * @param endIndex the index to stop joining from (exclusive). It is
     * an error to pass in an end index past the end of the list
     * @return the joined String, `null` if null list input
     * @since 3.8
     */
    fun join(list: List<*>?, separator: String?, startIndex: Int, endIndex: Int): String? {
        if (list == null) {
            return null
        }
        val noOfItems = endIndex - startIndex
        if (noOfItems <= 0) {
            return EMPTY
        }
        val subList = list.subList(startIndex, endIndex)
        return join(subList.iterator(), separator)
    }

    /**
     *
     * Joins the elements of the provided varargs into a
     * single String containing the provided elements.
     *
     *
     * No delimiter is added before or after the list.
     * `null` elements and separator are treated as empty Strings ("").
     *
     * <pre>
     * StringUtils.joinWith(",", {"a", "b"})        = "a,b"
     * StringUtils.joinWith(",", {"a", "b",""})     = "a,b,"
     * StringUtils.joinWith(",", {"a", null, "b"})  = "a,,b"
     * StringUtils.joinWith(null, {"a", "b"})       = "ab"
    </pre> *
     *
     * @param separator the separator character to use, null treated as ""
     * @param objects the varargs providing the values to join together. `null` elements are treated as ""
     * @return the joined String.
     * @throws IllegalArgumentException if a null varargs is provided
     * @since 3.5
     */
    fun joinWith(separator: String?, vararg objects: Any?): String {
        requireNotNull(objects) { "Object varargs must not be null" }
        val sanitizedSeparator = defaultString(separator)
        val result = StringBuilder()
        val iterator: Iterator<Any> = Arrays.asList<Any>(*objects).iterator()
        while (iterator.hasNext()) {
            val value = Objects.toString(iterator.next(), "")
            result.append(value)
            if (iterator.hasNext()) {
                result.append(sanitizedSeparator)
            }
        }
        return result.toString()
    }
    // Delete
//-----------------------------------------------------------------------
    /**
     *
     * Deletes all whitespaces from a String as defined by
     * [Character.isWhitespace].
     *
     * <pre>
     * StringUtils.deleteWhitespace(null)         = null
     * StringUtils.deleteWhitespace("")           = ""
     * StringUtils.deleteWhitespace("abc")        = "abc"
     * StringUtils.deleteWhitespace("   ab  c  ") = "abc"
    </pre> *
     *
     * @param str  the String to delete whitespace from, may be null
     * @return the String without whitespaces, `null` if null String input
     */
    fun deleteWhitespace(str: String): String {
        if (isEmpty(str)) {
            return str
        }
        val sz = str.length
        val chs = CharArray(sz)
        var count = 0
        for (i in 0 until sz) {
            if (!Character.isWhitespace(str[i])) {
                chs[count++] = str[i]
            }
        }
        return if (count == sz) {
            str
        } else String(chs, 0, count)
    }
    // Remove
//-----------------------------------------------------------------------
    /**
     *
     * Removes a substring only if it is at the beginning of a source string,
     * otherwise returns the source string.
     *
     *
     * A `null` source string will return `null`.
     * An empty ("") source string will return the empty string.
     * A `null` search string will return the source string.
     *
     * <pre>
     * StringUtils.removeStart(null, *)      = null
     * StringUtils.removeStart("", *)        = ""
     * StringUtils.removeStart(*, null)      = *
     * StringUtils.removeStart("www.domain.com", "www.")   = "domain.com"
     * StringUtils.removeStart("domain.com", "www.")       = "domain.com"
     * StringUtils.removeStart("www.domain.com", "domain") = "www.domain.com"
     * StringUtils.removeStart("abc", "")    = "abc"
    </pre> *
     *
     * @param str  the source String to search, may be null
     * @param remove  the String to search for and remove, may be null
     * @return the substring with the string removed if found,
     * `null` if null String input
     * @since 2.1
     */
    fun removeStart(str: String, remove: String): String {
        if (isEmpty(str) || isEmpty(remove)) {
            return str
        }
        return if (str.startsWith(remove)) {
            str.substring(remove.length)
        } else str
    }

    /**
     *
     * Case insensitive removal of a substring if it is at the beginning of a source string,
     * otherwise returns the source string.
     *
     *
     * A `null` source string will return `null`.
     * An empty ("") source string will return the empty string.
     * A `null` search string will return the source string.
     *
     * <pre>
     * StringUtils.removeStartIgnoreCase(null, *)      = null
     * StringUtils.removeStartIgnoreCase("", *)        = ""
     * StringUtils.removeStartIgnoreCase(*, null)      = *
     * StringUtils.removeStartIgnoreCase("www.domain.com", "www.")   = "domain.com"
     * StringUtils.removeStartIgnoreCase("www.domain.com", "WWW.")   = "domain.com"
     * StringUtils.removeStartIgnoreCase("domain.com", "www.")       = "domain.com"
     * StringUtils.removeStartIgnoreCase("www.domain.com", "domain") = "www.domain.com"
     * StringUtils.removeStartIgnoreCase("abc", "")    = "abc"
    </pre> *
     *
     * @param str  the source String to search, may be null
     * @param remove  the String to search for (case insensitive) and remove, may be null
     * @return the substring with the string removed if found,
     * `null` if null String input
     * @since 2.4
     */
    fun removeStartIgnoreCase(str: String, remove: String): String {
        if (isEmpty(str) || isEmpty(remove)) {
            return str
        }
        return if (StringUtils.startsWithIgnoreCase(str, remove)) {
            str.substring(remove.length)
        } else str
    }

    /**
     *
     * Removes a substring only if it is at the end of a source string,
     * otherwise returns the source string.
     *
     *
     * A `null` source string will return `null`.
     * An empty ("") source string will return the empty string.
     * A `null` search string will return the source string.
     *
     * <pre>
     * StringUtils.removeEnd(null, *)      = null
     * StringUtils.removeEnd("", *)        = ""
     * StringUtils.removeEnd(*, null)      = *
     * StringUtils.removeEnd("www.domain.com", ".com.")  = "www.domain.com"
     * StringUtils.removeEnd("www.domain.com", ".com")   = "www.domain"
     * StringUtils.removeEnd("www.domain.com", "domain") = "www.domain.com"
     * StringUtils.removeEnd("abc", "")    = "abc"
    </pre> *
     *
     * @param str  the source String to search, may be null
     * @param remove  the String to search for and remove, may be null
     * @return the substring with the string removed if found,
     * `null` if null String input
     * @since 2.1
     */
    fun removeEnd(str: String?, remove: String): String? {
        if (isEmpty(str) || isEmpty(remove)) {
            return str
        }
        return if (str!!.endsWith(remove)) {
            str.substring(0, str.length - remove.length)
        } else str
    }

    /**
     *
     * Case insensitive removal of a substring if it is at the end of a source string,
     * otherwise returns the source string.
     *
     *
     * A `null` source string will return `null`.
     * An empty ("") source string will return the empty string.
     * A `null` search string will return the source string.
     *
     * <pre>
     * StringUtils.removeEndIgnoreCase(null, *)      = null
     * StringUtils.removeEndIgnoreCase("", *)        = ""
     * StringUtils.removeEndIgnoreCase(*, null)      = *
     * StringUtils.removeEndIgnoreCase("www.domain.com", ".com.")  = "www.domain.com"
     * StringUtils.removeEndIgnoreCase("www.domain.com", ".com")   = "www.domain"
     * StringUtils.removeEndIgnoreCase("www.domain.com", "domain") = "www.domain.com"
     * StringUtils.removeEndIgnoreCase("abc", "")    = "abc"
     * StringUtils.removeEndIgnoreCase("www.domain.com", ".COM") = "www.domain")
     * StringUtils.removeEndIgnoreCase("www.domain.COM", ".com") = "www.domain")
    </pre> *
     *
     * @param str  the source String to search, may be null
     * @param remove  the String to search for (case insensitive) and remove, may be null
     * @return the substring with the string removed if found,
     * `null` if null String input
     * @since 2.4
     */
    fun removeEndIgnoreCase(str: String, remove: String): String {
        if (isEmpty(str) || isEmpty(remove)) {
            return str
        }
        return if (StringUtils.endsWithIgnoreCase(str, remove)) {
            str.substring(0, str.length - remove.length)
        } else str
    }

    /**
     *
     * Removes all occurrences of a substring from within the source string.
     *
     *
     * A `null` source string will return `null`.
     * An empty ("") source string will return the empty string.
     * A `null` remove string will return the source string.
     * An empty ("") remove string will return the source string.
     *
     * <pre>
     * StringUtils.remove(null, *)        = null
     * StringUtils.remove("", *)          = ""
     * StringUtils.remove(*, null)        = *
     * StringUtils.remove(*, "")          = *
     * StringUtils.remove("queued", "ue") = "qd"
     * StringUtils.remove("queued", "zz") = "queued"
    </pre> *
     *
     * @param str  the source String to search, may be null
     * @param remove  the String to search for and remove, may be null
     * @return the substring with the string removed if found,
     * `null` if null String input
     * @since 2.1
     */
    fun remove(str: String, remove: String): String {
        return if (isEmpty(str) || isEmpty(remove)) {
            str
        } else replace(str, remove, EMPTY, -1)
    }

    /**
     *
     *
     * Case insensitive removal of all occurrences of a substring from within
     * the source string.
     *
     *
     *
     *
     * A `null` source string will return `null`. An empty ("")
     * source string will return the empty string. A `null` remove string
     * will return the source string. An empty ("") remove string will return
     * the source string.
     *
     *
     * <pre>
     * StringUtils.removeIgnoreCase(null, *)        = null
     * StringUtils.removeIgnoreCase("", *)          = ""
     * StringUtils.removeIgnoreCase(*, null)        = *
     * StringUtils.removeIgnoreCase(*, "")          = *
     * StringUtils.removeIgnoreCase("queued", "ue") = "qd"
     * StringUtils.removeIgnoreCase("queued", "zz") = "queued"
     * StringUtils.removeIgnoreCase("quEUed", "UE") = "qd"
     * StringUtils.removeIgnoreCase("queued", "zZ") = "queued"
    </pre> *
     *
     * @param str
     * the source String to search, may be null
     * @param remove
     * the String to search for (case insensitive) and remove, may be
     * null
     * @return the substring with the string removed if found, `null` if
     * null String input
     * @since 3.5
     */
    fun removeIgnoreCase(str: String, remove: String): String {
        return if (isEmpty(str) || isEmpty(remove)) {
            str
        } else replaceIgnoreCase(str, remove, EMPTY, -1)
    }

    /**
     *
     * Removes all occurrences of a character from within the source string.
     *
     *
     * A `null` source string will return `null`.
     * An empty ("") source string will return the empty string.
     *
     * <pre>
     * StringUtils.remove(null, *)       = null
     * StringUtils.remove("", *)         = ""
     * StringUtils.remove("queued", 'u') = "qeed"
     * StringUtils.remove("queued", 'z') = "queued"
    </pre> *
     *
     * @param str  the source String to search, may be null
     * @param remove  the char to search for and remove, may be null
     * @return the substring with the char removed if found,
     * `null` if null String input
     * @since 2.1
     */
    fun remove(str: String, remove: Char): String {
        if (isEmpty(str) || str.indexOf(remove) == INDEX_NOT_FOUND) {
            return str
        }
        val chars = str.toCharArray()
        var pos = 0
        for (i in chars.indices) {
            if (chars[i] != remove) {
                chars[pos++] = chars[i]
            }
        }
        return String(chars, 0, pos)
    }

    /**
     *
     * Removes each substring of the text String that matches the given regular expression.
     *
     * This method is a `null` safe equivalent to:
     *
     *  * `text.replaceAll(regex, StringUtils.EMPTY)`
     *  * `Pattern.compile(regex).matcher(text).replaceAll(StringUtils.EMPTY)`
     *
     *
     *
     * A `null` reference passed to this method is a no-op.
     *
     *
     * Unlike in the [.removePattern] method, the [Pattern.DOTALL] option
     * is NOT automatically added.
     * To use the DOTALL option prepend `"(?s)"` to the regex.
     * DOTALL is also known as single-line mode in Perl.
     *
     * <pre>
     * StringUtils.removeAll(null, *)      = null
     * StringUtils.removeAll("any", (String) null)  = "any"
     * StringUtils.removeAll("any", "")    = "any"
     * StringUtils.removeAll("any", ".*")  = ""
     * StringUtils.removeAll("any", ".+")  = ""
     * StringUtils.removeAll("abc", ".?")  = ""
     * StringUtils.removeAll("A&lt;__&gt;\n&lt;__&gt;B", "&lt;.*&gt;")      = "A\nB"
     * StringUtils.removeAll("A&lt;__&gt;\n&lt;__&gt;B", "(?s)&lt;.*&gt;")  = "AB"
     * StringUtils.removeAll("ABCabc123abc", "[a-z]")     = "ABC123"
    </pre> *
     *
     * @param text  text to remove from, may be null
     * @param regex  the regular expression to which this string is to be matched
     * @return  the text with any removes processed,
     * `null` if null String input
     *
     * @throws  java.util.regex.PatternSyntaxException
     * if the regular expression's syntax is invalid
     *
     * @see .replaceAll
     * @see .removePattern
     * @see String.replaceAll
     * @see Pattern
     *
     * @see Pattern.DOTALL
     *
     * @since 3.5
     *
     */
    @Deprecated("Moved to RegExUtils.")
    fun removeAll(text: String?, regex: String?): String {
        return RegExUtils.removeAll(text, regex)
    }

    /**
     *
     * Removes the first substring of the text string that matches the given regular expression.
     *
     * This method is a `null` safe equivalent to:
     *
     *  * `text.replaceFirst(regex, StringUtils.EMPTY)`
     *  * `Pattern.compile(regex).matcher(text).replaceFirst(StringUtils.EMPTY)`
     *
     *
     *
     * A `null` reference passed to this method is a no-op.
     *
     *
     * The [Pattern.DOTALL] option is NOT automatically added.
     * To use the DOTALL option prepend `"(?s)"` to the regex.
     * DOTALL is also known as single-line mode in Perl.
     *
     * <pre>
     * StringUtils.removeFirst(null, *)      = null
     * StringUtils.removeFirst("any", (String) null)  = "any"
     * StringUtils.removeFirst("any", "")    = "any"
     * StringUtils.removeFirst("any", ".*")  = ""
     * StringUtils.removeFirst("any", ".+")  = ""
     * StringUtils.removeFirst("abc", ".?")  = "bc"
     * StringUtils.removeFirst("A&lt;__&gt;\n&lt;__&gt;B", "&lt;.*&gt;")      = "A\n&lt;__&gt;B"
     * StringUtils.removeFirst("A&lt;__&gt;\n&lt;__&gt;B", "(?s)&lt;.*&gt;")  = "AB"
     * StringUtils.removeFirst("ABCabc123", "[a-z]")          = "ABCbc123"
     * StringUtils.removeFirst("ABCabc123abc", "[a-z]+")      = "ABC123abc"
    </pre> *
     *
     * @param text  text to remove from, may be null
     * @param regex  the regular expression to which this string is to be matched
     * @return  the text with the first replacement processed,
     * `null` if null String input
     *
     * @throws  java.util.regex.PatternSyntaxException
     * if the regular expression's syntax is invalid
     *
     * @see .replaceFirst
     * @see String.replaceFirst
     * @see Pattern
     *
     * @see Pattern.DOTALL
     *
     * @since 3.5
     *
     */
    @Deprecated("Moved to RegExUtils.")
    fun removeFirst(text: String?, regex: String?): String {
        return replaceFirst(text, regex, EMPTY)
    }
    // Replacing
//-----------------------------------------------------------------------
    /**
     *
     * Replaces a String with another String inside a larger String, once.
     *
     *
     * A `null` reference passed to this method is a no-op.
     *
     * <pre>
     * StringUtils.replaceOnce(null, *, *)        = null
     * StringUtils.replaceOnce("", *, *)          = ""
     * StringUtils.replaceOnce("any", null, *)    = "any"
     * StringUtils.replaceOnce("any", *, null)    = "any"
     * StringUtils.replaceOnce("any", "", *)      = "any"
     * StringUtils.replaceOnce("aba", "a", null)  = "aba"
     * StringUtils.replaceOnce("aba", "a", "")    = "ba"
     * StringUtils.replaceOnce("aba", "a", "z")   = "zba"
    </pre> *
     *
     * @see .replace
     * @param text  text to search and replace in, may be null
     * @param searchString  the String to search for, may be null
     * @param replacement  the String to replace with, may be null
     * @return the text with any replacements processed,
     * `null` if null String input
     */
    fun replaceOnce(text: String, searchString: String, replacement: String?): String {
        return replace(text, searchString, replacement, 1)
    }

    /**
     *
     * Case insensitively replaces a String with another String inside a larger String, once.
     *
     *
     * A `null` reference passed to this method is a no-op.
     *
     * <pre>
     * StringUtils.replaceOnceIgnoreCase(null, *, *)        = null
     * StringUtils.replaceOnceIgnoreCase("", *, *)          = ""
     * StringUtils.replaceOnceIgnoreCase("any", null, *)    = "any"
     * StringUtils.replaceOnceIgnoreCase("any", *, null)    = "any"
     * StringUtils.replaceOnceIgnoreCase("any", "", *)      = "any"
     * StringUtils.replaceOnceIgnoreCase("aba", "a", null)  = "aba"
     * StringUtils.replaceOnceIgnoreCase("aba", "a", "")    = "ba"
     * StringUtils.replaceOnceIgnoreCase("aba", "a", "z")   = "zba"
     * StringUtils.replaceOnceIgnoreCase("FoOFoofoo", "foo", "") = "Foofoo"
    </pre> *
     *
     * @see .replaceIgnoreCase
     * @param text  text to search and replace in, may be null
     * @param searchString  the String to search for (case insensitive), may be null
     * @param replacement  the String to replace with, may be null
     * @return the text with any replacements processed,
     * `null` if null String input
     * @since 3.5
     */
    fun replaceOnceIgnoreCase(text: String, searchString: String, replacement: String?): String {
        return replaceIgnoreCase(text, searchString, replacement, 1)
    }

    /**
     *
     * Replaces each substring of the source String that matches the given regular expression with the given
     * replacement using the [Pattern.DOTALL] option. DOTALL is also known as single-line mode in Perl.
     *
     * This call is a `null` safe equivalent to:
     *
     *  * `source.replaceAll(&quot;(?s)&quot; + regex, replacement)`
     *  * `Pattern.compile(regex, Pattern.DOTALL).matcher(source).replaceAll(replacement)`
     *
     *
     *
     * A `null` reference passed to this method is a no-op.
     *
     * <pre>
     * StringUtils.replacePattern(null, *, *)       = null
     * StringUtils.replacePattern("any", (String) null, *)   = "any"
     * StringUtils.replacePattern("any", *, null)   = "any"
     * StringUtils.replacePattern("", "", "zzz")    = "zzz"
     * StringUtils.replacePattern("", ".*", "zzz")  = "zzz"
     * StringUtils.replacePattern("", ".+", "zzz")  = ""
     * StringUtils.replacePattern("&lt;__&gt;\n&lt;__&gt;", "&lt;.*&gt;", "z")       = "z"
     * StringUtils.replacePattern("ABCabc123", "[a-z]", "_")       = "ABC___123"
     * StringUtils.replacePattern("ABCabc123", "[^A-Z0-9]+", "_")  = "ABC_123"
     * StringUtils.replacePattern("ABCabc123", "[^A-Z0-9]+", "")   = "ABC123"
     * StringUtils.replacePattern("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2")  = "Lorem_ipsum_dolor_sit"
    </pre> *
     *
     * @param source
     * the source string
     * @param regex
     * the regular expression to which this string is to be matched
     * @param replacement
     * the string to be substituted for each match
     * @return The resulting `String`
     * @see .replaceAll
     * @see String.replaceAll
     * @see Pattern.DOTALL
     *
     * @since 3.2
     * @since 3.5 Changed `null` reference passed to this method is a no-op.
     *
     */
    @Deprecated("Moved to RegExUtils.")
    fun replacePattern(source: String?, regex: String?, replacement: String?): String {
        return RegExUtils.replacePattern(source, regex, replacement)
    }

    /**
     *
     * Removes each substring of the source String that matches the given regular expression using the DOTALL option.
     *
     *
     * This call is a `null` safe equivalent to:
     *
     *  * `source.replaceAll(&quot;(?s)&quot; + regex, StringUtils.EMPTY)`
     *  * `Pattern.compile(regex, Pattern.DOTALL).matcher(source).replaceAll(StringUtils.EMPTY)`
     *
     *
     *
     * A `null` reference passed to this method is a no-op.
     *
     * <pre>
     * StringUtils.removePattern(null, *)       = null
     * StringUtils.removePattern("any", (String) null)   = "any"
     * StringUtils.removePattern("A&lt;__&gt;\n&lt;__&gt;B", "&lt;.*&gt;")  = "AB"
     * StringUtils.removePattern("ABCabc123", "[a-z]")    = "ABC123"
    </pre> *
     *
     * @param source
     * the source string
     * @param regex
     * the regular expression to which this string is to be matched
     * @return The resulting `String`
     * @see .replacePattern
     * @see String.replaceAll
     * @see Pattern.DOTALL
     *
     * @since 3.2
     * @since 3.5 Changed `null` reference passed to this method is a no-op.
     *
     */
    @Deprecated("Moved to RegExUtils.")
    fun removePattern(source: String?, regex: String?): String {
        return RegExUtils.removePattern(source, regex)
    }

    /**
     *
     * Replaces each substring of the text String that matches the given regular expression
     * with the given replacement.
     *
     * This method is a `null` safe equivalent to:
     *
     *  * `text.replaceAll(regex, replacement)`
     *  * `Pattern.compile(regex).matcher(text).replaceAll(replacement)`
     *
     *
     *
     * A `null` reference passed to this method is a no-op.
     *
     *
     * Unlike in the [.replacePattern] method, the [Pattern.DOTALL] option
     * is NOT automatically added.
     * To use the DOTALL option prepend `"(?s)"` to the regex.
     * DOTALL is also known as single-line mode in Perl.
     *
     * <pre>
     * StringUtils.replaceAll(null, *, *)       = null
     * StringUtils.replaceAll("any", (String) null, *)   = "any"
     * StringUtils.replaceAll("any", *, null)   = "any"
     * StringUtils.replaceAll("", "", "zzz")    = "zzz"
     * StringUtils.replaceAll("", ".*", "zzz")  = "zzz"
     * StringUtils.replaceAll("", ".+", "zzz")  = ""
     * StringUtils.replaceAll("abc", "", "ZZ")  = "ZZaZZbZZcZZ"
     * StringUtils.replaceAll("&lt;__&gt;\n&lt;__&gt;", "&lt;.*&gt;", "z")      = "z\nz"
     * StringUtils.replaceAll("&lt;__&gt;\n&lt;__&gt;", "(?s)&lt;.*&gt;", "z")  = "z"
     * StringUtils.replaceAll("ABCabc123", "[a-z]", "_")       = "ABC___123"
     * StringUtils.replaceAll("ABCabc123", "[^A-Z0-9]+", "_")  = "ABC_123"
     * StringUtils.replaceAll("ABCabc123", "[^A-Z0-9]+", "")   = "ABC123"
     * StringUtils.replaceAll("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2")  = "Lorem_ipsum_dolor_sit"
    </pre> *
     *
     * @param text  text to search and replace in, may be null
     * @param regex  the regular expression to which this string is to be matched
     * @param replacement  the string to be substituted for each match
     * @return  the text with any replacements processed,
     * `null` if null String input
     *
     * @throws  java.util.regex.PatternSyntaxException
     * if the regular expression's syntax is invalid
     *
     * @see .replacePattern
     * @see String.replaceAll
     * @see Pattern
     *
     * @see Pattern.DOTALL
     *
     * @since 3.5
     *
     */
    @Deprecated("Moved to RegExUtils.")
    fun replaceAll(text: String?, regex: String?, replacement: String?): String {
        return RegExUtils.replaceAll(text, regex, replacement)
    }

    /**
     *
     * Replaces the first substring of the text string that matches the given regular expression
     * with the given replacement.
     *
     * This method is a `null` safe equivalent to:
     *
     *  * `text.replaceFirst(regex, replacement)`
     *  * `Pattern.compile(regex).matcher(text).replaceFirst(replacement)`
     *
     *
     *
     * A `null` reference passed to this method is a no-op.
     *
     *
     * The [Pattern.DOTALL] option is NOT automatically added.
     * To use the DOTALL option prepend `"(?s)"` to the regex.
     * DOTALL is also known as single-line mode in Perl.
     *
     * <pre>
     * StringUtils.replaceFirst(null, *, *)       = null
     * StringUtils.replaceFirst("any", (String) null, *)   = "any"
     * StringUtils.replaceFirst("any", *, null)   = "any"
     * StringUtils.replaceFirst("", "", "zzz")    = "zzz"
     * StringUtils.replaceFirst("", ".*", "zzz")  = "zzz"
     * StringUtils.replaceFirst("", ".+", "zzz")  = ""
     * StringUtils.replaceFirst("abc", "", "ZZ")  = "ZZabc"
     * StringUtils.replaceFirst("&lt;__&gt;\n&lt;__&gt;", "&lt;.*&gt;", "z")      = "z\n&lt;__&gt;"
     * StringUtils.replaceFirst("&lt;__&gt;\n&lt;__&gt;", "(?s)&lt;.*&gt;", "z")  = "z"
     * StringUtils.replaceFirst("ABCabc123", "[a-z]", "_")          = "ABC_bc123"
     * StringUtils.replaceFirst("ABCabc123abc", "[^A-Z0-9]+", "_")  = "ABC_123abc"
     * StringUtils.replaceFirst("ABCabc123abc", "[^A-Z0-9]+", "")   = "ABC123abc"
     * StringUtils.replaceFirst("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2")  = "Lorem_ipsum  dolor   sit"
    </pre> *
     *
     * @param text  text to search and replace in, may be null
     * @param regex  the regular expression to which this string is to be matched
     * @param replacement  the string to be substituted for the first match
     * @return  the text with the first replacement processed,
     * `null` if null String input
     *
     * @throws  java.util.regex.PatternSyntaxException
     * if the regular expression's syntax is invalid
     *
     * @see String.replaceFirst
     * @see Pattern
     *
     * @see Pattern.DOTALL
     *
     * @since 3.5
     *
     */
    @Deprecated("Moved to RegExUtils.")
    fun replaceFirst(text: String?, regex: String?, replacement: String?): String {
        return RegExUtils.replaceFirst(text, regex, replacement)
    }
    /**
     *
     * Replaces a String with another String inside a larger String,
     * for the first `max` values of the search String.
     *
     *
     * A `null` reference passed to this method is a no-op.
     *
     * <pre>
     * StringUtils.replace(null, *, *, *)         = null
     * StringUtils.replace("", *, *, *)           = ""
     * StringUtils.replace("any", null, *, *)     = "any"
     * StringUtils.replace("any", *, null, *)     = "any"
     * StringUtils.replace("any", "", *, *)       = "any"
     * StringUtils.replace("any", *, *, 0)        = "any"
     * StringUtils.replace("abaa", "a", null, -1) = "abaa"
     * StringUtils.replace("abaa", "a", "", -1)   = "b"
     * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
     * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
     * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
     * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
    </pre> *
     *
     * @param text  text to search and replace in, may be null
     * @param searchString  the String to search for, may be null
     * @param replacement  the String to replace it with, may be null
     * @param max  maximum number of values to replace, or `-1` if no maximum
     * @return the text with any replacements processed,
     * `null` if null String input
     */
    /**
     *
     * Replaces all occurrences of a String within another String.
     *
     *
     * A `null` reference passed to this method is a no-op.
     *
     * <pre>
     * StringUtils.replace(null, *, *)        = null
     * StringUtils.replace("", *, *)          = ""
     * StringUtils.replace("any", null, *)    = "any"
     * StringUtils.replace("any", *, null)    = "any"
     * StringUtils.replace("any", "", *)      = "any"
     * StringUtils.replace("aba", "a", null)  = "aba"
     * StringUtils.replace("aba", "a", "")    = "b"
     * StringUtils.replace("aba", "a", "z")   = "zbz"
    </pre> *
     *
     * @see .replace
     * @param text  text to search and replace in, may be null
     * @param searchString  the String to search for, may be null
     * @param replacement  the String to replace it with, may be null
     * @return the text with any replacements processed,
     * `null` if null String input
     */
    @JvmOverloads
    fun replace(text: String, searchString: String, replacement: String?, max: Int = -1): String {
        return replace(text, searchString, replacement, max, false)
    }

    /**
     *
     * Replaces a String with another String inside a larger String,
     * for the first `max` values of the search String,
     * case sensitively/insensisitively based on `ignoreCase` value.
     *
     *
     * A `null` reference passed to this method is a no-op.
     *
     * <pre>
     * StringUtils.replace(null, *, *, *, false)         = null
     * StringUtils.replace("", *, *, *, false)           = ""
     * StringUtils.replace("any", null, *, *, false)     = "any"
     * StringUtils.replace("any", *, null, *, false)     = "any"
     * StringUtils.replace("any", "", *, *, false)       = "any"
     * StringUtils.replace("any", *, *, 0, false)        = "any"
     * StringUtils.replace("abaa", "a", null, -1, false) = "abaa"
     * StringUtils.replace("abaa", "a", "", -1, false)   = "b"
     * StringUtils.replace("abaa", "a", "z", 0, false)   = "abaa"
     * StringUtils.replace("abaa", "A", "z", 1, false)   = "abaa"
     * StringUtils.replace("abaa", "A", "z", 1, true)   = "zbaa"
     * StringUtils.replace("abAa", "a", "z", 2, true)   = "zbza"
     * StringUtils.replace("abAa", "a", "z", -1, true)  = "zbzz"
    </pre> *
     *
     * @param text  text to search and replace in, may be null
     * @param searchString  the String to search for (case insensitive), may be null
     * @param replacement  the String to replace it with, may be null
     * @param max  maximum number of values to replace, or `-1` if no maximum
     * @param ignoreCase if true replace is case insensitive, otherwise case sensitive
     * @return the text with any replacements processed,
     * `null` if null String input
     */
    private fun replace(
        text: String,
        searchString: String,
        replacement: String?,
        max: Int,
        ignoreCase: Boolean
    ): String {
        var searchString = searchString
        var max = max
        if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
            return text
        }
        var searchText = text
        if (ignoreCase) {
            searchText = text.toLowerCase()
            searchString = searchString.toLowerCase()
        }
        var start = 0
        var end = searchText.indexOf(searchString, start)
        if (end == INDEX_NOT_FOUND) {
            return text
        }
        val replLength = searchString.length
        var increase = replacement.length - replLength
        increase = if (increase < 0) 0 else increase
        increase *= if (max < 0) 16 else if (max > 64) 64 else max
        val buf = StringBuilder(text.length + increase)
        while (end != INDEX_NOT_FOUND) {
            buf.append(text, start, end).append(replacement)
            start = end + replLength
            if (--max == 0) {
                break
            }
            end = searchText.indexOf(searchString, start)
        }
        buf.append(text, start, text.length)
        return buf.toString()
    }
    /**
     *
     * Case insensitively replaces a String with another String inside a larger String,
     * for the first `max` values of the search String.
     *
     *
     * A `null` reference passed to this method is a no-op.
     *
     * <pre>
     * StringUtils.replaceIgnoreCase(null, *, *, *)         = null
     * StringUtils.replaceIgnoreCase("", *, *, *)           = ""
     * StringUtils.replaceIgnoreCase("any", null, *, *)     = "any"
     * StringUtils.replaceIgnoreCase("any", *, null, *)     = "any"
     * StringUtils.replaceIgnoreCase("any", "", *, *)       = "any"
     * StringUtils.replaceIgnoreCase("any", *, *, 0)        = "any"
     * StringUtils.replaceIgnoreCase("abaa", "a", null, -1) = "abaa"
     * StringUtils.replaceIgnoreCase("abaa", "a", "", -1)   = "b"
     * StringUtils.replaceIgnoreCase("abaa", "a", "z", 0)   = "abaa"
     * StringUtils.replaceIgnoreCase("abaa", "A", "z", 1)   = "zbaa"
     * StringUtils.replaceIgnoreCase("abAa", "a", "z", 2)   = "zbza"
     * StringUtils.replaceIgnoreCase("abAa", "a", "z", -1)  = "zbzz"
    </pre> *
     *
     * @param text  text to search and replace in, may be null
     * @param searchString  the String to search for (case insensitive), may be null
     * @param replacement  the String to replace it with, may be null
     * @param max  maximum number of values to replace, or `-1` if no maximum
     * @return the text with any replacements processed,
     * `null` if null String input
     * @since 3.5
     */
    /**
     *
     * Case insensitively replaces all occurrences of a String within another String.
     *
     *
     * A `null` reference passed to this method is a no-op.
     *
     * <pre>
     * StringUtils.replaceIgnoreCase(null, *, *)        = null
     * StringUtils.replaceIgnoreCase("", *, *)          = ""
     * StringUtils.replaceIgnoreCase("any", null, *)    = "any"
     * StringUtils.replaceIgnoreCase("any", *, null)    = "any"
     * StringUtils.replaceIgnoreCase("any", "", *)      = "any"
     * StringUtils.replaceIgnoreCase("aba", "a", null)  = "aba"
     * StringUtils.replaceIgnoreCase("abA", "A", "")    = "b"
     * StringUtils.replaceIgnoreCase("aba", "A", "z")   = "zbz"
    </pre> *
     *
     * @see .replaceIgnoreCase
     * @param text  text to search and replace in, may be null
     * @param searchString  the String to search for (case insensitive), may be null
     * @param replacement  the String to replace it with, may be null
     * @return the text with any replacements processed,
     * `null` if null String input
     * @since 3.5
     */
    @JvmOverloads
    fun replaceIgnoreCase(text: String, searchString: String, replacement: String?, max: Int = -1): String {
        return replace(text, searchString, replacement, max, true)
    }

    /**
     *
     *
     * Replaces all occurrences of Strings within another String.
     *
     *
     *
     *
     * A `null` reference passed to this method is a no-op, or if
     * any "search string" or "string to replace" is null, that replace will be
     * ignored. This will not repeat. For repeating replaces, call the
     * overloaded method.
     *
     *
     * <pre>
     * StringUtils.replaceEach(null, *, *)        = null
     * StringUtils.replaceEach("", *, *)          = ""
     * StringUtils.replaceEach("aba", null, null) = "aba"
     * StringUtils.replaceEach("aba", new String[0], null) = "aba"
     * StringUtils.replaceEach("aba", null, new String[0]) = "aba"
     * StringUtils.replaceEach("aba", new String[]{"a"}, null)  = "aba"
     * StringUtils.replaceEach("aba", new String[]{"a"}, new String[]{""})  = "b"
     * StringUtils.replaceEach("aba", new String[]{null}, new String[]{"a"})  = "aba"
     * StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"})  = "wcte"
     * (example of how it does not repeat)
     * StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"})  = "dcte"
    </pre> *
     *
     * @param text
     * text to search and replace in, no-op if null
     * @param searchList
     * the Strings to search for, no-op if null
     * @param replacementList
     * the Strings to replace them with, no-op if null
     * @return the text with any replacements processed, `null` if
     * null String input
     * @throws IllegalArgumentException
     * if the lengths of the arrays are not the same (null is ok,
     * and/or size 0)
     * @since 2.4
     */
    fun replaceEach(
        text: String?,
        searchList: Array<String?>?,
        replacementList: Array<String?>?
    ): String? {
        return replaceEach(text, searchList, replacementList, false, 0)
    }

    /**
     *
     *
     * Replaces all occurrences of Strings within another String.
     *
     *
     *
     *
     * A `null` reference passed to this method is a no-op, or if
     * any "search string" or "string to replace" is null, that replace will be
     * ignored.
     *
     *
     * <pre>
     * StringUtils.replaceEachRepeatedly(null, *, *) = null
     * StringUtils.replaceEachRepeatedly("", *, *) = ""
     * StringUtils.replaceEachRepeatedly("aba", null, null) = "aba"
     * StringUtils.replaceEachRepeatedly("aba", new String[0], null) = "aba"
     * StringUtils.replaceEachRepeatedly("aba", null, new String[0]) = "aba"
     * StringUtils.replaceEachRepeatedly("aba", new String[]{"a"}, null) = "aba"
     * StringUtils.replaceEachRepeatedly("aba", new String[]{"a"}, new String[]{""}) = "b"
     * StringUtils.replaceEachRepeatedly("aba", new String[]{null}, new String[]{"a"}) = "aba"
     * StringUtils.replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"}) = "wcte"
     * (example of how it repeats)
     * StringUtils.replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}) = "tcte"
     * StringUtils.replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"d", "ab"}) = IllegalStateException
    </pre> *
     *
     * @param text
     * text to search and replace in, no-op if null
     * @param searchList
     * the Strings to search for, no-op if null
     * @param replacementList
     * the Strings to replace them with, no-op if null
     * @return the text with any replacements processed, `null` if
     * null String input
     * @throws IllegalStateException
     * if the search is repeating and there is an endless loop due
     * to outputs of one being inputs to another
     * @throws IllegalArgumentException
     * if the lengths of the arrays are not the same (null is ok,
     * and/or size 0)
     * @since 2.4
     */
    fun replaceEachRepeatedly(
        text: String?,
        searchList: Array<String?>?,
        replacementList: Array<String?>?
    ): String? { // timeToLive should be 0 if not used or nothing to replace, else it's
// the length of the replace array
        val timeToLive = searchList?.size ?: 0
        return replaceEach(text, searchList, replacementList, true, timeToLive)
    }

    /**
     *
     *
     * Replace all occurrences of Strings within another String.
     * This is a private recursive helper method for [.replaceEachRepeatedly] and
     * [.replaceEach]
     *
     *
     *
     *
     * A `null` reference passed to this method is a no-op, or if
     * any "search string" or "string to replace" is null, that replace will be
     * ignored.
     *
     *
     * <pre>
     * StringUtils.replaceEach(null, *, *, *, *) = null
     * StringUtils.replaceEach("", *, *, *, *) = ""
     * StringUtils.replaceEach("aba", null, null, *, *) = "aba"
     * StringUtils.replaceEach("aba", new String[0], null, *, *) = "aba"
     * StringUtils.replaceEach("aba", null, new String[0], *, *) = "aba"
     * StringUtils.replaceEach("aba", new String[]{"a"}, null, *, *) = "aba"
     * StringUtils.replaceEach("aba", new String[]{"a"}, new String[]{""}, *, >=0) = "b"
     * StringUtils.replaceEach("aba", new String[]{null}, new String[]{"a"}, *, >=0) = "aba"
     * StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"}, *, >=0) = "wcte"
     * (example of how it repeats)
     * StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, false, >=0) = "dcte"
     * StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, true, >=2) = "tcte"
     * StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "ab"}, *, *) = IllegalStateException
    </pre> *
     *
     * @param text
     * text to search and replace in, no-op if null
     * @param searchList
     * the Strings to search for, no-op if null
     * @param replacementList
     * the Strings to replace them with, no-op if null
     * @param repeat if true, then replace repeatedly
     * until there are no more possible replacements or timeToLive < 0
     * @param timeToLive
     * if less than 0 then there is a circular reference and endless
     * loop
     * @return the text with any replacements processed, `null` if
     * null String input
     * @throws IllegalStateException
     * if the search is repeating and there is an endless loop due
     * to outputs of one being inputs to another
     * @throws IllegalArgumentException
     * if the lengths of the arrays are not the same (null is ok,
     * and/or size 0)
     * @since 2.4
     */
    private fun replaceEach(
        text: String?,
        searchList: Array<String?>?,
        replacementList: Array<String?>?,
        repeat: Boolean,
        timeToLive: Int
    ): String? { // mchyzer Performance note: This creates very few new objects (one major goal)
// let me know if there are performance requests, we can create a harness to measure
        if (text == null || text.isEmpty() || searchList == null || searchList.size == 0 || replacementList == null || replacementList.size == 0
        ) {
            return text
        }
        // if recursing, this shouldn't be less than 0
        check(timeToLive >= 0) {
            "Aborting to protect against StackOverflowError - " +
                    "output of one loop is the input of another"
        }
        val searchLength = searchList.size
        val replacementLength = replacementList.size
        // make sure lengths are ok, these need to be equal
        require(searchLength == replacementLength) {
            ("Search and Replace array lengths don't match: "
                    + searchLength
                    + " vs "
                    + replacementLength)
        }
        // keep track of which still have matches
        val noMoreMatchesForReplIndex = BooleanArray(searchLength)
        // index on index that the match was found
        var textIndex = -1
        var replaceIndex = -1
        var tempIndex = -1
        // index of replace array that will replace the search string found
// NOTE: logic duplicated below START
        for (i in 0 until searchLength) {
            if (noMoreMatchesForReplIndex[i] || searchList[i] == null ||
                searchList[i]!!.isEmpty() || replacementList[i] == null
            ) {
                continue
            }
            tempIndex = text.indexOf(searchList[i]!!)
            // see if we need to keep searching for this
            if (tempIndex == -1) {
                noMoreMatchesForReplIndex[i] = true
            } else {
                if (textIndex == -1 || tempIndex < textIndex) {
                    textIndex = tempIndex
                    replaceIndex = i
                }
            }
        }
        // NOTE: logic mostly below END
// no search strings found, we are done
        if (textIndex == -1) {
            return text
        }
        var start = 0
        // get a good guess on the size of the result buffer so it doesn't have to double if it goes over a bit
        var increase = 0
        // count the replacement text elements that are larger than their corresponding text being replaced
        for (i in searchList.indices) {
            if (searchList[i] == null || replacementList[i] == null) {
                continue
            }
            val greater = replacementList[i]!!.length - searchList[i]!!.length
            if (greater > 0) {
                increase += 3 * greater // assume 3 matches
            }
        }
        // have upper-bound at 20% increase, then let Java take over
        increase = Math.min(increase, text.length / 5)
        val buf = StringBuilder(text.length + increase)
        while (textIndex != -1) {
            for (i in start until textIndex) {
                buf.append(text[i])
            }
            buf.append(replacementList[replaceIndex])
            start = textIndex + searchList[replaceIndex]!!.length
            textIndex = -1
            replaceIndex = -1
            tempIndex = -1
            // find the next earliest match
// NOTE: logic mostly duplicated above START
            for (i in 0 until searchLength) {
                if (noMoreMatchesForReplIndex[i] || searchList[i] == null ||
                    searchList[i]!!.isEmpty() || replacementList[i] == null
                ) {
                    continue
                }
                tempIndex = text.indexOf(searchList[i]!!, start)
                // see if we need to keep searching for this
                if (tempIndex == -1) {
                    noMoreMatchesForReplIndex[i] = true
                } else {
                    if (textIndex == -1 || tempIndex < textIndex) {
                        textIndex = tempIndex
                        replaceIndex = i
                    }
                }
            }
            // NOTE: logic duplicated above END
        }
        val textLength = text.length
        for (i in start until textLength) {
            buf.append(text[i])
        }
        val result = buf.toString()
        return if (!repeat) {
            result
        } else replaceEach(result, searchList, replacementList, repeat, timeToLive - 1)
    }
    // Replace, character based
//-----------------------------------------------------------------------
    /**
     *
     * Replaces all occurrences of a character in a String with another.
     * This is a null-safe version of [String.replace].
     *
     *
     * A `null` string input returns `null`.
     * An empty ("") string input returns an empty string.
     *
     * <pre>
     * StringUtils.replaceChars(null, *, *)        = null
     * StringUtils.replaceChars("", *, *)          = ""
     * StringUtils.replaceChars("abcba", 'b', 'y') = "aycya"
     * StringUtils.replaceChars("abcba", 'z', 'y') = "abcba"
    </pre> *
     *
     * @param str  String to replace characters in, may be null
     * @param searchChar  the character to search for, may be null
     * @param replaceChar  the character to replace, may be null
     * @return modified String, `null` if null string input
     * @since 2.0
     */
    fun replaceChars(str: String?, searchChar: Char, replaceChar: Char): String? {
        return str?.replace(searchChar, replaceChar)
    }

    /**
     *
     * Replaces multiple characters in a String in one go.
     * This method can also be used to delete characters.
     *
     *
     * For example:<br></br>
     * `replaceChars("hello", "ho", "jy") = jelly`.
     *
     *
     * A `null` string input returns `null`.
     * An empty ("") string input returns an empty string.
     * A null or empty set of search characters returns the input string.
     *
     *
     * The length of the search characters should normally equal the length
     * of the replace characters.
     * If the search characters is longer, then the extra search characters
     * are deleted.
     * If the search characters is shorter, then the extra replace characters
     * are ignored.
     *
     * <pre>
     * StringUtils.replaceChars(null, *, *)           = null
     * StringUtils.replaceChars("", *, *)             = ""
     * StringUtils.replaceChars("abc", null, *)       = "abc"
     * StringUtils.replaceChars("abc", "", *)         = "abc"
     * StringUtils.replaceChars("abc", "b", null)     = "ac"
     * StringUtils.replaceChars("abc", "b", "")       = "ac"
     * StringUtils.replaceChars("abcba", "bc", "yz")  = "ayzya"
     * StringUtils.replaceChars("abcba", "bc", "y")   = "ayya"
     * StringUtils.replaceChars("abcba", "bc", "yzx") = "ayzya"
    </pre> *
     *
     * @param str  String to replace characters in, may be null
     * @param searchChars  a set of characters to search for, may be null
     * @param replaceChars  a set of characters to replace, may be null
     * @return modified String, `null` if null string input
     * @since 2.0
     */
    fun replaceChars(str: String, searchChars: String, replaceChars: String?): String {
        var replaceChars = replaceChars
        if (isEmpty(str) || isEmpty(searchChars)) {
            return str
        }
        if (replaceChars == null) {
            replaceChars = EMPTY
        }
        var modified = false
        val replaceCharsLength = replaceChars.length
        val strLength = str.length
        val buf = StringBuilder(strLength)
        for (i in 0 until strLength) {
            val ch = str[i]
            val index = searchChars.indexOf(ch)
            if (index >= 0) {
                modified = true
                if (index < replaceCharsLength) {
                    buf.append(replaceChars[index])
                }
            } else {
                buf.append(ch)
            }
        }
        return if (modified) {
            buf.toString()
        } else str
    }
    // Overlay
//-----------------------------------------------------------------------
    /**
     *
     * Overlays part of a String with another String.
     *
     *
     * A `null` string input returns `null`.
     * A negative index is treated as zero.
     * An index greater than the string length is treated as the string length.
     * The start index is always the smaller of the two indices.
     *
     * <pre>
     * StringUtils.overlay(null, *, *, *)            = null
     * StringUtils.overlay("", "abc", 0, 0)          = "abc"
     * StringUtils.overlay("abcdef", null, 2, 4)     = "abef"
     * StringUtils.overlay("abcdef", "", 2, 4)       = "abef"
     * StringUtils.overlay("abcdef", "", 4, 2)       = "abef"
     * StringUtils.overlay("abcdef", "zzzz", 2, 4)   = "abzzzzef"
     * StringUtils.overlay("abcdef", "zzzz", 4, 2)   = "abzzzzef"
     * StringUtils.overlay("abcdef", "zzzz", -1, 4)  = "zzzzef"
     * StringUtils.overlay("abcdef", "zzzz", 2, 8)   = "abzzzz"
     * StringUtils.overlay("abcdef", "zzzz", -2, -3) = "zzzzabcdef"
     * StringUtils.overlay("abcdef", "zzzz", 8, 10)  = "abcdefzzzz"
    </pre> *
     *
     * @param str  the String to do overlaying in, may be null
     * @param overlay  the String to overlay, may be null
     * @param start  the position to start overlaying at
     * @param end  the position to stop overlaying before
     * @return overlayed String, `null` if null String input
     * @since 2.0
     */
    fun overlay(str: String?, overlay: String?, start: Int, end: Int): String? {
        var overlay = overlay
        var start = start
        var end = end
        if (str == null) {
            return null
        }
        if (overlay == null) {
            overlay = EMPTY
        }
        val len = str.length
        if (start < 0) {
            start = 0
        }
        if (start > len) {
            start = len
        }
        if (end < 0) {
            end = 0
        }
        if (end > len) {
            end = len
        }
        if (start > end) {
            val temp = start
            start = end
            end = temp
        }
        return str.substring(0, start) +
                overlay +
                str.substring(end)
    }
    // Chomping
//-----------------------------------------------------------------------
    /**
     *
     * Removes one newline from end of a String if it's there,
     * otherwise leave it alone.  A newline is &quot;`\n`&quot;,
     * &quot;`\r`&quot;, or &quot;`\r\n`&quot;.
     *
     *
     * NOTE: This method changed in 2.0.
     * It now more closely matches Perl chomp.
     *
     * <pre>
     * StringUtils.chomp(null)          = null
     * StringUtils.chomp("")            = ""
     * StringUtils.chomp("abc \r")      = "abc "
     * StringUtils.chomp("abc\n")       = "abc"
     * StringUtils.chomp("abc\r\n")     = "abc"
     * StringUtils.chomp("abc\r\n\r\n") = "abc\r\n"
     * StringUtils.chomp("abc\n\r")     = "abc\n"
     * StringUtils.chomp("abc\n\rabc")  = "abc\n\rabc"
     * StringUtils.chomp("\r")          = ""
     * StringUtils.chomp("\n")          = ""
     * StringUtils.chomp("\r\n")        = ""
    </pre> *
     *
     * @param str  the String to chomp a newline from, may be null
     * @return String without newline, `null` if null String input
     */
    fun chomp(str: String): String {
        if (isEmpty(str)) {
            return str
        }
        if (str.length == 1) {
            val ch = str[0]
            return if (ch == CharUtils.CR || ch == CharUtils.LF) {
                EMPTY
            } else str
        }
        var lastIdx = str.length - 1
        val last = str[lastIdx]
        if (last == CharUtils.LF) {
            if (str[lastIdx - 1] == CharUtils.CR) {
                lastIdx--
            }
        } else if (last != CharUtils.CR) {
            lastIdx++
        }
        return str.substring(0, lastIdx)
    }

    /**
     *
     * Removes `separator` from the end of
     * `str` if it's there, otherwise leave it alone.
     *
     *
     * NOTE: This method changed in version 2.0.
     * It now more closely matches Perl chomp.
     * For the previous behavior, use [.substringBeforeLast].
     * This method uses [String.endsWith].
     *
     * <pre>
     * StringUtils.chomp(null, *)         = null
     * StringUtils.chomp("", *)           = ""
     * StringUtils.chomp("foobar", "bar") = "foo"
     * StringUtils.chomp("foobar", "baz") = "foobar"
     * StringUtils.chomp("foo", "foo")    = ""
     * StringUtils.chomp("foo ", "foo")   = "foo "
     * StringUtils.chomp(" foo", "foo")   = " "
     * StringUtils.chomp("foo", "foooo")  = "foo"
     * StringUtils.chomp("foo", "")       = "foo"
     * StringUtils.chomp("foo", null)     = "foo"
    </pre> *
     *
     * @param str  the String to chomp from, may be null
     * @param separator  separator String, may be null
     * @return String without trailing separator, `null` if null String input
     */
    @Deprecated("This feature will be removed in Lang 4.0, use {@link StringUtils#removeEnd(String, String)} instead")
    fun chomp(str: String?, separator: String): String? {
        return removeEnd(str, separator)
    }
    // Chopping
//-----------------------------------------------------------------------
    /**
     *
     * Remove the last character from a String.
     *
     *
     * If the String ends in `\r\n`, then remove both
     * of them.
     *
     * <pre>
     * StringUtils.chop(null)          = null
     * StringUtils.chop("")            = ""
     * StringUtils.chop("abc \r")      = "abc "
     * StringUtils.chop("abc\n")       = "abc"
     * StringUtils.chop("abc\r\n")     = "abc"
     * StringUtils.chop("abc")         = "ab"
     * StringUtils.chop("abc\nabc")    = "abc\nab"
     * StringUtils.chop("a")           = ""
     * StringUtils.chop("\r")          = ""
     * StringUtils.chop("\n")          = ""
     * StringUtils.chop("\r\n")        = ""
    </pre> *
     *
     * @param str  the String to chop last character from, may be null
     * @return String without last character, `null` if null String input
     */
    fun chop(str: String?): String? {
        if (str == null) {
            return null
        }
        val strLen = str.length
        if (strLen < 2) {
            return EMPTY
        }
        val lastIdx = strLen - 1
        val ret = str.substring(0, lastIdx)
        val last = str[lastIdx]
        return if (last == CharUtils.LF && ret[lastIdx - 1] == CharUtils.CR) {
            ret.substring(0, lastIdx - 1)
        } else ret
    }
    // Conversion
//-----------------------------------------------------------------------
// Padding
//-----------------------------------------------------------------------
    /**
     *
     * Repeat a String `repeat` times to form a
     * new String.
     *
     * <pre>
     * StringUtils.repeat(null, 2) = null
     * StringUtils.repeat("", 0)   = ""
     * StringUtils.repeat("", 2)   = ""
     * StringUtils.repeat("a", 3)  = "aaa"
     * StringUtils.repeat("ab", 2) = "abab"
     * StringUtils.repeat("a", -2) = ""
    </pre> *
     *
     * @param str  the String to repeat, may be null
     * @param repeat  number of times to repeat str, negative treated as zero
     * @return a new String consisting of the original String repeated,
     * `null` if null String input
     */
    fun repeat(str: String?, repeat: Int): String? { // Performance tuned for 2.0 (JDK1.4)
        if (str == null) {
            return null
        }
        if (repeat <= 0) {
            return EMPTY
        }
        val inputLength = str.length
        if (repeat == 1 || inputLength == 0) {
            return str
        }
        if (inputLength == 1 && repeat <= PAD_LIMIT) {
            return repeat(str[0], repeat)
        }
        val outputLength = inputLength * repeat
        return when (inputLength) {
            1 -> repeat(str[0], repeat)
            2 -> {
                val ch0 = str[0]
                val ch1 = str[1]
                val output2 = CharArray(outputLength)
                var i = repeat * 2 - 2
                while (i >= 0) {
                    output2[i] = ch0
                    output2[i + 1] = ch1
                    i--
                    i--
                }
                String(output2)
            }
            else -> {
                val buf = StringBuilder(outputLength)
                var i = 0
                while (i < repeat) {
                    buf.append(str)
                    i++
                }
                buf.toString()
            }
        }
    }

    /**
     *
     * Repeat a String `repeat` times to form a
     * new String, with a String separator injected each time.
     *
     * <pre>
     * StringUtils.repeat(null, null, 2) = null
     * StringUtils.repeat(null, "x", 2)  = null
     * StringUtils.repeat("", null, 0)   = ""
     * StringUtils.repeat("", "", 2)     = ""
     * StringUtils.repeat("", "x", 3)    = "xxx"
     * StringUtils.repeat("?", ", ", 3)  = "?, ?, ?"
    </pre> *
     *
     * @param str        the String to repeat, may be null
     * @param separator  the String to inject, may be null
     * @param repeat     number of times to repeat str, negative treated as zero
     * @return a new String consisting of the original String repeated,
     * `null` if null String input
     * @since 2.5
     */
    fun repeat(str: String?, separator: String?, repeat: Int): String? {
        if (str == null || separator == null) {
            return repeat(str, repeat)
        }
        // given that repeat(String, int) is quite optimized, better to rely on it than try and splice this into it
        val result = repeat(str + separator, repeat)
        return removeEnd(result, separator)
    }

    /**
     *
     * Returns padding using the specified delimiter repeated
     * to a given length.
     *
     * <pre>
     * StringUtils.repeat('e', 0)  = ""
     * StringUtils.repeat('e', 3)  = "eee"
     * StringUtils.repeat('e', -2) = ""
    </pre> *
     *
     *
     * Note: this method does not support padding with
     * [Unicode Supplementary Characters](http://www.unicode.org/glossary/#supplementary_character)
     * as they require a pair of `char`s to be represented.
     * If you are needing to support full I18N of your applications
     * consider using [.repeat] instead.
     *
     *
     * @param ch  character to repeat
     * @param repeat  number of times to repeat char, negative treated as zero
     * @return String with repeated character
     * @see .repeat
     */
    fun repeat(ch: Char, repeat: Int): String {
        if (repeat <= 0) {
            return EMPTY
        }
        val buf = CharArray(repeat)
        for (i in repeat - 1 downTo 0) {
            buf[i] = ch
        }
        return String(buf)
    }
    /**
     *
     * Right pad a String with a specified character.
     *
     *
     * The String is padded to the size of `size`.
     *
     * <pre>
     * StringUtils.rightPad(null, *, *)     = null
     * StringUtils.rightPad("", 3, 'z')     = "zzz"
     * StringUtils.rightPad("bat", 3, 'z')  = "bat"
     * StringUtils.rightPad("bat", 5, 'z')  = "batzz"
     * StringUtils.rightPad("bat", 1, 'z')  = "bat"
     * StringUtils.rightPad("bat", -1, 'z') = "bat"
    </pre> *
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padChar  the character to pad with
     * @return right padded String or original String if no padding is necessary,
     * `null` if null String input
     * @since 2.0
     */
    /**
     *
     * Right pad a String with spaces (' ').
     *
     *
     * The String is padded to the size of `size`.
     *
     * <pre>
     * StringUtils.rightPad(null, *)   = null
     * StringUtils.rightPad("", 3)     = "   "
     * StringUtils.rightPad("bat", 3)  = "bat"
     * StringUtils.rightPad("bat", 5)  = "bat  "
     * StringUtils.rightPad("bat", 1)  = "bat"
     * StringUtils.rightPad("bat", -1) = "bat"
    </pre> *
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @return right padded String or original String if no padding is necessary,
     * `null` if null String input
     */
    @JvmOverloads
    fun rightPad(str: String?, size: Int, padChar: Char = ' '): String? {
        if (str == null) {
            return null
        }
        val pads = size - str.length
        if (pads <= 0) {
            return str // returns original String when possible
        }
        return if (pads > PAD_LIMIT) {
            rightPad(str, size, padChar.toString())
        } else str + repeat(padChar, pads)
    }

    /**
     *
     * Right pad a String with a specified String.
     *
     *
     * The String is padded to the size of `size`.
     *
     * <pre>
     * StringUtils.rightPad(null, *, *)      = null
     * StringUtils.rightPad("", 3, "z")      = "zzz"
     * StringUtils.rightPad("bat", 3, "yz")  = "bat"
     * StringUtils.rightPad("bat", 5, "yz")  = "batyz"
     * StringUtils.rightPad("bat", 8, "yz")  = "batyzyzy"
     * StringUtils.rightPad("bat", 1, "yz")  = "bat"
     * StringUtils.rightPad("bat", -1, "yz") = "bat"
     * StringUtils.rightPad("bat", 5, null)  = "bat  "
     * StringUtils.rightPad("bat", 5, "")    = "bat  "
    </pre> *
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padStr  the String to pad with, null or empty treated as single space
     * @return right padded String or original String if no padding is necessary,
     * `null` if null String input
     */
    fun rightPad(str: String?, size: Int, padStr: String): String? {
        var padStr = padStr
        if (str == null) {
            return null
        }
        if (isEmpty(padStr)) {
            padStr = SPACE
        }
        val padLen = padStr.length
        val strLen = str.length
        val pads = size - strLen
        if (pads <= 0) {
            return str // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return rightPad(str, size, padStr[0])
        }
        return if (pads == padLen) {
            str + padStr
        } else if (pads < padLen) {
            str + padStr.substring(0, pads)
        } else {
            val padding = CharArray(pads)
            val padChars = padStr.toCharArray()
            for (i in 0 until pads) {
                padding[i] = padChars[i % padLen]
            }
            str + String(padding)
        }
    }
    /**
     *
     * Left pad a String with a specified character.
     *
     *
     * Pad to a size of `size`.
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)     = null
     * StringUtils.leftPad("", 3, 'z')     = "zzz"
     * StringUtils.leftPad("bat", 3, 'z')  = "bat"
     * StringUtils.leftPad("bat", 5, 'z')  = "zzbat"
     * StringUtils.leftPad("bat", 1, 'z')  = "bat"
     * StringUtils.leftPad("bat", -1, 'z') = "bat"
    </pre> *
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padChar  the character to pad with
     * @return left padded String or original String if no padding is necessary,
     * `null` if null String input
     * @since 2.0
     */
    /**
     *
     * Left pad a String with spaces (' ').
     *
     *
     * The String is padded to the size of `size`.
     *
     * <pre>
     * StringUtils.leftPad(null, *)   = null
     * StringUtils.leftPad("", 3)     = "   "
     * StringUtils.leftPad("bat", 3)  = "bat"
     * StringUtils.leftPad("bat", 5)  = "  bat"
     * StringUtils.leftPad("bat", 1)  = "bat"
     * StringUtils.leftPad("bat", -1) = "bat"
    </pre> *
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @return left padded String or original String if no padding is necessary,
     * `null` if null String input
     */
    @JvmOverloads
    fun leftPad(str: String?, size: Int, padChar: Char = ' '): String? {
        if (str == null) {
            return null
        }
        val pads = size - str.length
        if (pads <= 0) {
            return str // returns original String when possible
        }
        return if (pads > PAD_LIMIT) {
            leftPad(str, size, padChar.toString())
        } else repeat(padChar, pads) + str
    }

    /**
     *
     * Left pad a String with a specified String.
     *
     *
     * Pad to a size of `size`.
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)      = null
     * StringUtils.leftPad("", 3, "z")      = "zzz"
     * StringUtils.leftPad("bat", 3, "yz")  = "bat"
     * StringUtils.leftPad("bat", 5, "yz")  = "yzbat"
     * StringUtils.leftPad("bat", 8, "yz")  = "yzyzybat"
     * StringUtils.leftPad("bat", 1, "yz")  = "bat"
     * StringUtils.leftPad("bat", -1, "yz") = "bat"
     * StringUtils.leftPad("bat", 5, null)  = "  bat"
     * StringUtils.leftPad("bat", 5, "")    = "  bat"
    </pre> *
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padStr  the String to pad with, null or empty treated as single space
     * @return left padded String or original String if no padding is necessary,
     * `null` if null String input
     */
    fun leftPad(str: String?, size: Int, padStr: String): String? {
        var padStr = padStr
        if (str == null) {
            return null
        }
        if (isEmpty(padStr)) {
            padStr = SPACE
        }
        val padLen = padStr.length
        val strLen = str.length
        val pads = size - strLen
        if (pads <= 0) {
            return str // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, padStr[0])
        }
        return if (pads == padLen) {
            padStr + str
        } else if (pads < padLen) {
            padStr.substring(0, pads) + str
        } else {
            val padding = CharArray(pads)
            val padChars = padStr.toCharArray()
            for (i in 0 until pads) {
                padding[i] = padChars[i % padLen]
            }
            String(padding) + str
        }
    }

    /**
     * Gets a CharSequence length or `0` if the CharSequence is
     * `null`.
     *
     * @param cs
     * a CharSequence or `null`
     * @return CharSequence length or `0` if the CharSequence is
     * `null`.
     * @since 2.4
     * @since 3.0 Changed signature from length(String) to length(CharSequence)
     */
    fun length(cs: CharSequence?): Int {
        return cs?.length ?: 0
    }
    /**
     *
     * Centers a String in a larger String of size `size`.
     * Uses a supplied character as the value to pad the String with.
     *
     *
     * If the size is less than the String length, the String is returned.
     * A `null` String returns `null`.
     * A negative size is treated as zero.
     *
     * <pre>
     * StringUtils.center(null, *, *)     = null
     * StringUtils.center("", 4, ' ')     = "    "
     * StringUtils.center("ab", -1, ' ')  = "ab"
     * StringUtils.center("ab", 4, ' ')   = " ab "
     * StringUtils.center("abcd", 2, ' ') = "abcd"
     * StringUtils.center("a", 4, ' ')    = " a  "
     * StringUtils.center("a", 4, 'y')    = "yayy"
    </pre> *
     *
     * @param str  the String to center, may be null
     * @param size  the int size of new String, negative treated as zero
     * @param padChar  the character to pad the new String with
     * @return centered String, `null` if null String input
     * @since 2.0
     */
// Centering
//-----------------------------------------------------------------------
    /**
     *
     * Centers a String in a larger String of size `size`
     * using the space character (' ').
     *
     *
     * If the size is less than the String length, the String is returned.
     * A `null` String returns `null`.
     * A negative size is treated as zero.
     *
     *
     * Equivalent to `center(str, size, " ")`.
     *
     * <pre>
     * StringUtils.center(null, *)   = null
     * StringUtils.center("", 4)     = "    "
     * StringUtils.center("ab", -1)  = "ab"
     * StringUtils.center("ab", 4)   = " ab "
     * StringUtils.center("abcd", 2) = "abcd"
     * StringUtils.center("a", 4)    = " a  "
    </pre> *
     *
     * @param str  the String to center, may be null
     * @param size  the int size of new String, negative treated as zero
     * @return centered String, `null` if null String input
     */
    @JvmOverloads
    fun center(str: String?, size: Int, padChar: Char = ' '): String? {
        var str = str
        if (str == null || size <= 0) {
            return str
        }
        val strLen = str.length
        val pads = size - strLen
        if (pads <= 0) {
            return str
        }
        str = leftPad(str, strLen + pads / 2, padChar)
        str = rightPad(str, size, padChar)
        return str
    }

    /**
     *
     * Centers a String in a larger String of size `size`.
     * Uses a supplied String as the value to pad the String with.
     *
     *
     * If the size is less than the String length, the String is returned.
     * A `null` String returns `null`.
     * A negative size is treated as zero.
     *
     * <pre>
     * StringUtils.center(null, *, *)     = null
     * StringUtils.center("", 4, " ")     = "    "
     * StringUtils.center("ab", -1, " ")  = "ab"
     * StringUtils.center("ab", 4, " ")   = " ab "
     * StringUtils.center("abcd", 2, " ") = "abcd"
     * StringUtils.center("a", 4, " ")    = " a  "
     * StringUtils.center("a", 4, "yz")   = "yayz"
     * StringUtils.center("abc", 7, null) = "  abc  "
     * StringUtils.center("abc", 7, "")   = "  abc  "
    </pre> *
     *
     * @param str  the String to center, may be null
     * @param size  the int size of new String, negative treated as zero
     * @param padStr  the String to pad the new String with, must not be null or empty
     * @return centered String, `null` if null String input
     * @throws IllegalArgumentException if padStr is `null` or empty
     */
    fun center(str: String?, size: Int, padStr: String): String? {
        var str = str
        var padStr = padStr
        if (str == null || size <= 0) {
            return str
        }
        if (isEmpty(padStr)) {
            padStr = SPACE
        }
        val strLen = str.length
        val pads = size - strLen
        if (pads <= 0) {
            return str
        }
        str = leftPad(str, strLen + pads / 2, padStr)
        str = rightPad(str, size, padStr)
        return str
    }
    // Case conversion
//-----------------------------------------------------------------------
    /**
     *
     * Converts a String to upper case as per [String.toUpperCase].
     *
     *
     * A `null` input String returns `null`.
     *
     * <pre>
     * StringUtils.upperCase(null)  = null
     * StringUtils.upperCase("")    = ""
     * StringUtils.upperCase("aBc") = "ABC"
    </pre> *
     *
     *
     * **Note:** As described in the documentation for [String.toUpperCase],
     * the result of this method is affected by the current locale.
     * For platform-independent case transformations, the method [.lowerCase]
     * should be used with a specific locale (e.g. [Locale.ENGLISH]).
     *
     * @param str  the String to upper case, may be null
     * @return the upper cased String, `null` if null String input
     */
    fun upperCase(str: String?): String? {
        return str?.toUpperCase()
    }

    /**
     *
     * Converts a String to upper case as per [String.toUpperCase].
     *
     *
     * A `null` input String returns `null`.
     *
     * <pre>
     * StringUtils.upperCase(null, Locale.ENGLISH)  = null
     * StringUtils.upperCase("", Locale.ENGLISH)    = ""
     * StringUtils.upperCase("aBc", Locale.ENGLISH) = "ABC"
    </pre> *
     *
     * @param str  the String to upper case, may be null
     * @param locale  the locale that defines the case transformation rules, must not be null
     * @return the upper cased String, `null` if null String input
     * @since 2.5
     */
    fun upperCase(str: String?, locale: Locale?): String? {
        return str?.toUpperCase(locale!!)
    }

    /**
     *
     * Converts a String to lower case as per [String.toLowerCase].
     *
     *
     * A `null` input String returns `null`.
     *
     * <pre>
     * StringUtils.lowerCase(null)  = null
     * StringUtils.lowerCase("")    = ""
     * StringUtils.lowerCase("aBc") = "abc"
    </pre> *
     *
     *
     * **Note:** As described in the documentation for [String.toLowerCase],
     * the result of this method is affected by the current locale.
     * For platform-independent case transformations, the method [.lowerCase]
     * should be used with a specific locale (e.g. [Locale.ENGLISH]).
     *
     * @param str  the String to lower case, may be null
     * @return the lower cased String, `null` if null String input
     */
    fun lowerCase(str: String?): String? {
        return str?.toLowerCase()
    }

    /**
     *
     * Converts a String to lower case as per [String.toLowerCase].
     *
     *
     * A `null` input String returns `null`.
     *
     * <pre>
     * StringUtils.lowerCase(null, Locale.ENGLISH)  = null
     * StringUtils.lowerCase("", Locale.ENGLISH)    = ""
     * StringUtils.lowerCase("aBc", Locale.ENGLISH) = "abc"
    </pre> *
     *
     * @param str  the String to lower case, may be null
     * @param locale  the locale that defines the case transformation rules, must not be null
     * @return the lower cased String, `null` if null String input
     * @since 2.5
     */
    fun lowerCase(str: String?, locale: Locale?): String? {
        return str?.toLowerCase(locale!!)
    }

    /**
     *
     * Capitalizes a String changing the first character to title case as
     * per [Character.toTitleCase]. No other characters are changed.
     *
     *
     * For a word based algorithm, see [org.apache.commons.lang3.text.WordUtils.capitalize].
     * A `null` input String returns `null`.
     *
     * <pre>
     * StringUtils.capitalize(null)  = null
     * StringUtils.capitalize("")    = ""
     * StringUtils.capitalize("cat") = "Cat"
     * StringUtils.capitalize("cAt") = "CAt"
     * StringUtils.capitalize("'cat'") = "'cat'"
    </pre> *
     *
     * @param str the String to capitalize, may be null
     * @return the capitalized String, `null` if null String input
     * @see org.apache.commons.lang3.text.WordUtils.capitalize
     * @see .uncapitalize
     * @since 2.0
     */
    fun capitalize(str: String?): String? {
        var strLen: Int
        if (str == null || str.length.also { strLen = it } == 0) {
            return str
        }
        val firstCodepoint = str.codePointAt(0)
        val newCodePoint = Character.toTitleCase(firstCodepoint)
        if (firstCodepoint == newCodePoint) { // already capitalized
            return str
        }
        val newCodePoints = IntArray(strLen) // cannot be longer than the char array
        var outOffset = 0
        newCodePoints[outOffset++] = newCodePoint // copy the first codepoint
        var inOffset = Character.charCount(firstCodepoint)
        while (inOffset < strLen) {
            val codepoint = str.codePointAt(inOffset)
            newCodePoints[outOffset++] = codepoint // copy the remaining ones
            inOffset += Character.charCount(codepoint)
        }
        return String(newCodePoints, 0, outOffset)
    }

    /**
     *
     * Uncapitalizes a String, changing the first character to lower case as
     * per [Character.toLowerCase]. No other characters are changed.
     *
     *
     * For a word based algorithm, see [org.apache.commons.lang3.text.WordUtils.uncapitalize].
     * A `null` input String returns `null`.
     *
     * <pre>
     * StringUtils.uncapitalize(null)  = null
     * StringUtils.uncapitalize("")    = ""
     * StringUtils.uncapitalize("cat") = "cat"
     * StringUtils.uncapitalize("Cat") = "cat"
     * StringUtils.uncapitalize("CAT") = "cAT"
    </pre> *
     *
     * @param str the String to uncapitalize, may be null
     * @return the uncapitalized String, `null` if null String input
     * @see org.apache.commons.lang3.text.WordUtils.uncapitalize
     * @see .capitalize
     * @since 2.0
     */
    fun uncapitalize(str: String?): String? {
        var strLen: Int
        if (str == null || str.length.also { strLen = it } == 0) {
            return str
        }
        val firstCodepoint = str.codePointAt(0)
        val newCodePoint = Character.toLowerCase(firstCodepoint)
        if (firstCodepoint == newCodePoint) { // already capitalized
            return str
        }
        val newCodePoints = IntArray(strLen) // cannot be longer than the char array
        var outOffset = 0
        newCodePoints[outOffset++] = newCodePoint // copy the first codepoint
        var inOffset = Character.charCount(firstCodepoint)
        while (inOffset < strLen) {
            val codepoint = str.codePointAt(inOffset)
            newCodePoints[outOffset++] = codepoint // copy the remaining ones
            inOffset += Character.charCount(codepoint)
        }
        return String(newCodePoints, 0, outOffset)
    }

    /**
     *
     * Swaps the case of a String changing upper and title case to
     * lower case, and lower case to upper case.
     *
     *
     *  * Upper case character converts to Lower case
     *  * Title case character converts to Lower case
     *  * Lower case character converts to Upper case
     *
     *
     *
     * For a word based algorithm, see [org.apache.commons.lang3.text.WordUtils.swapCase].
     * A `null` input String returns `null`.
     *
     * <pre>
     * StringUtils.swapCase(null)                 = null
     * StringUtils.swapCase("")                   = ""
     * StringUtils.swapCase("The dog has a BONE") = "tHE DOG HAS A bone"
    </pre> *
     *
     *
     * NOTE: This method changed in Lang version 2.0.
     * It no longer performs a word based algorithm.
     * If you only use ASCII, you will notice no change.
     * That functionality is available in org.apache.commons.lang3.text.WordUtils.
     *
     * @param str  the String to swap case, may be null
     * @return the changed String, `null` if null String input
     */
    fun swapCase(str: String): String {
        if (isEmpty(str)) {
            return str
        }
        val strLen = str.length
        val newCodePoints = IntArray(strLen) // cannot be longer than the char array
        var outOffset = 0
        var i = 0
        while (i < strLen) {
            val oldCodepoint = str.codePointAt(i)
            val newCodePoint: Int
            newCodePoint = if (Character.isUpperCase(oldCodepoint)) {
                Character.toLowerCase(oldCodepoint)
            } else if (Character.isTitleCase(oldCodepoint)) {
                Character.toLowerCase(oldCodepoint)
            } else if (Character.isLowerCase(oldCodepoint)) {
                Character.toUpperCase(oldCodepoint)
            } else {
                oldCodepoint
            }
            newCodePoints[outOffset++] = newCodePoint
            i += Character.charCount(newCodePoint)
        }
        return String(newCodePoints, 0, outOffset)
    }
    // Count matches
//-----------------------------------------------------------------------
    /**
     *
     * Counts how many times the substring appears in the larger string.
     *
     *
     * A `null` or empty ("") String input returns `0`.
     *
     * <pre>
     * StringUtils.countMatches(null, *)       = 0
     * StringUtils.countMatches("", *)         = 0
     * StringUtils.countMatches("abba", null)  = 0
     * StringUtils.countMatches("abba", "")    = 0
     * StringUtils.countMatches("abba", "a")   = 2
     * StringUtils.countMatches("abba", "ab")  = 1
     * StringUtils.countMatches("abba", "xxx") = 0
    </pre> *
     *
     * @param str  the CharSequence to check, may be null
     * @param sub  the substring to count, may be null
     * @return the number of occurrences, 0 if either CharSequence is `null`
     * @since 3.0 Changed signature from countMatches(String, String) to countMatches(CharSequence, CharSequence)
     */
    fun countMatches(str: CharSequence?, sub: CharSequence): Int {
        if (isEmpty(str) || isEmpty(sub)) {
            return 0
        }
        var count = 0
        var idx = 0
        while (CharSequenceUtils.indexOf(str, sub, idx).also {
                idx = it
            } != INDEX_NOT_FOUND) {
            count++
            idx += sub.length
        }
        return count
    }

    /**
     *
     * Counts how many times the char appears in the given string.
     *
     *
     * A `null` or empty ("") String input returns `0`.
     *
     * <pre>
     * StringUtils.countMatches(null, *)       = 0
     * StringUtils.countMatches("", *)         = 0
     * StringUtils.countMatches("abba", 0)  = 0
     * StringUtils.countMatches("abba", 'a')   = 2
     * StringUtils.countMatches("abba", 'b')  = 2
     * StringUtils.countMatches("abba", 'x') = 0
    </pre> *
     *
     * @param str  the CharSequence to check, may be null
     * @param ch  the char to count
     * @return the number of occurrences, 0 if the CharSequence is `null`
     * @since 3.4
     */
    fun countMatches(str: CharSequence, ch: Char): Int {
        if (isEmpty(str)) {
            return 0
        }
        var count = 0
        // We could also call str.toCharArray() for faster look ups but that would generate more garbage.
        for (i in 0 until str.length) {
            if (ch == str[i]) {
                count++
            }
        }
        return count
    }
    // Character Tests
//-----------------------------------------------------------------------
    /**
     *
     * Checks if the CharSequence contains only Unicode letters.
     *
     *
     * `null` will return `false`.
     * An empty CharSequence (length()=0) will return `false`.
     *
     * <pre>
     * StringUtils.isAlpha(null)   = false
     * StringUtils.isAlpha("")     = false
     * StringUtils.isAlpha("  ")   = false
     * StringUtils.isAlpha("abc")  = true
     * StringUtils.isAlpha("ab2c") = false
     * StringUtils.isAlpha("ab-c") = false
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @return `true` if only contains letters, and is non-null
     * @since 3.0 Changed signature from isAlpha(String) to isAlpha(CharSequence)
     * @since 3.0 Changed "" to return false and not true
     */
    fun isAlpha(cs: CharSequence): Boolean {
        if (isEmpty(cs)) {
            return false
        }
        val sz = cs.length
        for (i in 0 until sz) {
            if (!Character.isLetter(cs[i])) {
                return false
            }
        }
        return true
    }

    /**
     *
     * Checks if the CharSequence contains only Unicode letters and
     * space (' ').
     *
     *
     * `null` will return `false`
     * An empty CharSequence (length()=0) will return `true`.
     *
     * <pre>
     * StringUtils.isAlphaSpace(null)   = false
     * StringUtils.isAlphaSpace("")     = true
     * StringUtils.isAlphaSpace("  ")   = true
     * StringUtils.isAlphaSpace("abc")  = true
     * StringUtils.isAlphaSpace("ab c") = true
     * StringUtils.isAlphaSpace("ab2c") = false
     * StringUtils.isAlphaSpace("ab-c") = false
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @return `true` if only contains letters and space,
     * and is non-null
     * @since 3.0 Changed signature from isAlphaSpace(String) to isAlphaSpace(CharSequence)
     */
    fun isAlphaSpace(cs: CharSequence?): Boolean {
        if (cs == null) {
            return false
        }
        val sz = cs.length
        for (i in 0 until sz) {
            if (!Character.isLetter(cs[i]) && cs[i] != ' ') {
                return false
            }
        }
        return true
    }

    /**
     *
     * Checks if the CharSequence contains only Unicode letters or digits.
     *
     *
     * `null` will return `false`.
     * An empty CharSequence (length()=0) will return `false`.
     *
     * <pre>
     * StringUtils.isAlphanumeric(null)   = false
     * StringUtils.isAlphanumeric("")     = false
     * StringUtils.isAlphanumeric("  ")   = false
     * StringUtils.isAlphanumeric("abc")  = true
     * StringUtils.isAlphanumeric("ab c") = false
     * StringUtils.isAlphanumeric("ab2c") = true
     * StringUtils.isAlphanumeric("ab-c") = false
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @return `true` if only contains letters or digits,
     * and is non-null
     * @since 3.0 Changed signature from isAlphanumeric(String) to isAlphanumeric(CharSequence)
     * @since 3.0 Changed "" to return false and not true
     */
    fun isAlphanumeric(cs: CharSequence): Boolean {
        if (isEmpty(cs)) {
            return false
        }
        val sz = cs.length
        for (i in 0 until sz) {
            if (!Character.isLetterOrDigit(cs[i])) {
                return false
            }
        }
        return true
    }

    /**
     *
     * Checks if the CharSequence contains only Unicode letters, digits
     * or space (`' '`).
     *
     *
     * `null` will return `false`.
     * An empty CharSequence (length()=0) will return `true`.
     *
     * <pre>
     * StringUtils.isAlphanumericSpace(null)   = false
     * StringUtils.isAlphanumericSpace("")     = true
     * StringUtils.isAlphanumericSpace("  ")   = true
     * StringUtils.isAlphanumericSpace("abc")  = true
     * StringUtils.isAlphanumericSpace("ab c") = true
     * StringUtils.isAlphanumericSpace("ab2c") = true
     * StringUtils.isAlphanumericSpace("ab-c") = false
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @return `true` if only contains letters, digits or space,
     * and is non-null
     * @since 3.0 Changed signature from isAlphanumericSpace(String) to isAlphanumericSpace(CharSequence)
     */
    fun isAlphanumericSpace(cs: CharSequence?): Boolean {
        if (cs == null) {
            return false
        }
        val sz = cs.length
        for (i in 0 until sz) {
            if (!Character.isLetterOrDigit(cs[i]) && cs[i] != ' ') {
                return false
            }
        }
        return true
    }

    /**
     *
     * Checks if the CharSequence contains only ASCII printable characters.
     *
     *
     * `null` will return `false`.
     * An empty CharSequence (length()=0) will return `true`.
     *
     * <pre>
     * StringUtils.isAsciiPrintable(null)     = false
     * StringUtils.isAsciiPrintable("")       = true
     * StringUtils.isAsciiPrintable(" ")      = true
     * StringUtils.isAsciiPrintable("Ceki")   = true
     * StringUtils.isAsciiPrintable("ab2c")   = true
     * StringUtils.isAsciiPrintable("!ab-c~") = true
     * StringUtils.isAsciiPrintable("\u0020") = true
     * StringUtils.isAsciiPrintable("\u0021") = true
     * StringUtils.isAsciiPrintable("\u007e") = true
     * StringUtils.isAsciiPrintable("\u007f") = false
     * StringUtils.isAsciiPrintable("Ceki G\u00fclc\u00fc") = false
    </pre> *
     *
     * @param cs the CharSequence to check, may be null
     * @return `true` if every character is in the range
     * 32 thru 126
     * @since 2.1
     * @since 3.0 Changed signature from isAsciiPrintable(String) to isAsciiPrintable(CharSequence)
     */
    fun isAsciiPrintable(cs: CharSequence?): Boolean {
        if (cs == null) {
            return false
        }
        val sz = cs.length
        for (i in 0 until sz) {
            if (!CharUtils.isAsciiPrintable(cs[i])) {
                return false
            }
        }
        return true
    }

    /**
     *
     * Checks if the CharSequence contains only Unicode digits.
     * A decimal point is not a Unicode digit and returns false.
     *
     *
     * `null` will return `false`.
     * An empty CharSequence (length()=0) will return `false`.
     *
     *
     * Note that the method does not allow for a leading sign, either positive or negative.
     * Also, if a String passes the numeric test, it may still generate a NumberFormatException
     * when parsed by Integer.parseInt or Long.parseLong, e.g. if the value is outside the range
     * for int or long respectively.
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = false
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * StringUtils.isNumeric("-123") = false
     * StringUtils.isNumeric("+123") = false
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @return `true` if only contains digits, and is non-null
     * @since 3.0 Changed signature from isNumeric(String) to isNumeric(CharSequence)
     * @since 3.0 Changed "" to return false and not true
     */
    fun isNumeric(cs: CharSequence): Boolean {
        if (isEmpty(cs)) {
            return false
        }
        val sz = cs.length
        for (i in 0 until sz) {
            if (!Character.isDigit(cs[i])) {
                return false
            }
        }
        return true
    }

    /**
     *
     * Checks if the CharSequence contains only Unicode digits or space
     * (`' '`).
     * A decimal point is not a Unicode digit and returns false.
     *
     *
     * `null` will return `false`.
     * An empty CharSequence (length()=0) will return `true`.
     *
     * <pre>
     * StringUtils.isNumericSpace(null)   = false
     * StringUtils.isNumericSpace("")     = true
     * StringUtils.isNumericSpace("  ")   = true
     * StringUtils.isNumericSpace("123")  = true
     * StringUtils.isNumericSpace("12 3") = true
     * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
     * StringUtils.isNumeric("\u0967\u0968 \u0969")  = true
     * StringUtils.isNumericSpace("ab2c") = false
     * StringUtils.isNumericSpace("12-3") = false
     * StringUtils.isNumericSpace("12.3") = false
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @return `true` if only contains digits or space,
     * and is non-null
     * @since 3.0 Changed signature from isNumericSpace(String) to isNumericSpace(CharSequence)
     */
    fun isNumericSpace(cs: CharSequence?): Boolean {
        if (cs == null) {
            return false
        }
        val sz = cs.length
        for (i in 0 until sz) {
            if (!Character.isDigit(cs[i]) && cs[i] != ' ') {
                return false
            }
        }
        return true
    }

    /**
     *
     * Checks if a String `str` contains Unicode digits,
     * if yes then concatenate all the digits in `str` and return it as a String.
     *
     *
     * An empty ("") String will be returned if no digits found in `str`.
     *
     * <pre>
     * StringUtils.getDigits(null)  = null
     * StringUtils.getDigits("")    = ""
     * StringUtils.getDigits("abc") = ""
     * StringUtils.getDigits("1000$") = "1000"
     * StringUtils.getDigits("1123~45") = "112345"
     * StringUtils.getDigits("(541) 754-3010") = "5417543010"
     * StringUtils.getDigits("\u0967\u0968\u0969") = "\u0967\u0968\u0969"
    </pre> *
     *
     * @param str the String to extract digits from, may be null
     * @return String with only digits,
     * or an empty ("") String if no digits found,
     * or `null` String if `str` is null
     * @since 3.6
     */
    fun getDigits(str: String): String {
        if (isEmpty(str)) {
            return str
        }
        val sz = str.length
        val strDigits = StringBuilder(sz)
        for (i in 0 until sz) {
            val tempChar = str[i]
            if (Character.isDigit(tempChar)) {
                strDigits.append(tempChar)
            }
        }
        return strDigits.toString()
    }

    /**
     *
     * Checks if the CharSequence contains only whitespace.
     *
     *
     * Whitespace is defined by [Character.isWhitespace].
     *
     *
     * `null` will return `false`.
     * An empty CharSequence (length()=0) will return `true`.
     *
     * <pre>
     * StringUtils.isWhitespace(null)   = false
     * StringUtils.isWhitespace("")     = true
     * StringUtils.isWhitespace("  ")   = true
     * StringUtils.isWhitespace("abc")  = false
     * StringUtils.isWhitespace("ab2c") = false
     * StringUtils.isWhitespace("ab-c") = false
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @return `true` if only contains whitespace, and is non-null
     * @since 2.0
     * @since 3.0 Changed signature from isWhitespace(String) to isWhitespace(CharSequence)
     */
    fun isWhitespace(cs: CharSequence?): Boolean {
        if (cs == null) {
            return false
        }
        val sz = cs.length
        for (i in 0 until sz) {
            if (!Character.isWhitespace(cs[i])) {
                return false
            }
        }
        return true
    }

    /**
     *
     * Checks if the CharSequence contains only lowercase characters.
     *
     *
     * `null` will return `false`.
     * An empty CharSequence (length()=0) will return `false`.
     *
     * <pre>
     * StringUtils.isAllLowerCase(null)   = false
     * StringUtils.isAllLowerCase("")     = false
     * StringUtils.isAllLowerCase("  ")   = false
     * StringUtils.isAllLowerCase("abc")  = true
     * StringUtils.isAllLowerCase("abC")  = false
     * StringUtils.isAllLowerCase("ab c") = false
     * StringUtils.isAllLowerCase("ab1c") = false
     * StringUtils.isAllLowerCase("ab/c") = false
    </pre> *
     *
     * @param cs  the CharSequence to check, may be null
     * @return `true` if only contains lowercase characters, and is non-null
     * @since 2.5
     * @since 3.0 Changed signature from isAllLowerCase(String) to isAllLowerCase(CharSequence)
     */
    fun isAllLowerCase(cs: CharSequence?): Boolean {
        if (cs == null || isEmpty(cs)) {
            return false
        }
        val sz = cs.length
        for (i in 0 until sz) {
            if (!Character.isLowerCase(cs[i])) {
                return false
            }
        }
        return true
    }

    /**
     *
     * Checks if the CharSequence contains only uppercase characters.
     *
     *
     * `null` will return `false`.
     * An empty String (length()=0) will return `false`.
     *
     * <pre>
     * StringUtils.isAllUpperCase(null)   = false
     * StringUtils.isAllUpperCase("")     = false
     * StringUtils.isAllUpperCase("  ")   = false
     * StringUtils.isAllUpperCase("ABC")  = true
     * StringUtils.isAllUpperCase("aBC")  = false
     * StringUtils.isAllUpperCase("A C")  = false
     * StringUtils.isAllUpperCase("A1C")  = false
     * StringUtils.isAllUpperCase("A/C")  = false
    </pre> *
     *
     * @param cs the CharSequence to check, may be null
     * @return `true` if only contains uppercase characters, and is non-null
     * @since 2.5
     * @since 3.0 Changed signature from isAllUpperCase(String) to isAllUpperCase(CharSequence)
     */
    fun isAllUpperCase(cs: CharSequence?): Boolean {
        if (cs == null || isEmpty(cs)) {
            return false
        }
        val sz = cs.length
        for (i in 0 until sz) {
            if (!Character.isUpperCase(cs[i])) {
                return false
            }
        }
        return true
    }

    /**
     *
     * Checks if the CharSequence contains mixed casing of both uppercase and lowercase characters.
     *
     *
     * `null` will return `false`. An empty CharSequence (`length()=0`) will return
     * `false`.
     *
     * <pre>
     * StringUtils.isMixedCase(null)    = false
     * StringUtils.isMixedCase("")      = false
     * StringUtils.isMixedCase("ABC")   = false
     * StringUtils.isMixedCase("abc")   = false
     * StringUtils.isMixedCase("aBc")   = true
     * StringUtils.isMixedCase("A c")   = true
     * StringUtils.isMixedCase("A1c")   = true
     * StringUtils.isMixedCase("a/C")   = true
     * StringUtils.isMixedCase("aC\t")  = true
    </pre> *
     *
     * @param cs the CharSequence to check, may be null
     * @return `true` if the CharSequence contains both uppercase and lowercase characters
     * @since 3.5
     */
    fun isMixedCase(cs: CharSequence): Boolean {
        if (isEmpty(cs) || cs.length == 1) {
            return false
        }
        var containsUppercase = false
        var containsLowercase = false
        val sz = cs.length
        for (i in 0 until sz) {
            if (containsUppercase && containsLowercase) {
                return true
            } else if (Character.isUpperCase(cs[i])) {
                containsUppercase = true
            } else if (Character.isLowerCase(cs[i])) {
                containsLowercase = true
            }
        }
        return containsUppercase && containsLowercase
    }
    /**
     *
     * Returns either the passed in String, or if the String is
     * `null`, the value of `defaultStr`.
     *
     * <pre>
     * StringUtils.defaultString(null, "NULL")  = "NULL"
     * StringUtils.defaultString("", "NULL")    = ""
     * StringUtils.defaultString("bat", "NULL") = "bat"
    </pre> *
     *
     * @see ObjectUtils.toString
     * @see String.valueOf
     * @param str  the String to check, may be null
     * @param defaultStr  the default String to return
     * if the input is `null`, may be null
     * @return the passed in String, or the default if it was `null`
     */
// Defaults
//-----------------------------------------------------------------------
    /**
     *
     * Returns either the passed in String,
     * or if the String is `null`, an empty String ("").
     *
     * <pre>
     * StringUtils.defaultString(null)  = ""
     * StringUtils.defaultString("")    = ""
     * StringUtils.defaultString("bat") = "bat"
    </pre> *
     *
     * @see ObjectUtils.toString
     * @see String.valueOf
     * @param str  the String to check, may be null
     * @return the passed in String, or the empty String if it
     * was `null`
     */
    @JvmOverloads
    fun defaultString(str: String?, defaultStr: String = EMPTY): String {
        return str ?: defaultStr
    }

    /**
     *
     * Returns the first value in the array which is not empty (""),
     * `null` or whitespace only.
     *
     *
     * Whitespace is defined by [Character.isWhitespace].
     *
     *
     * If all values are blank or the array is `null`
     * or empty then `null` is returned.
     *
     * <pre>
     * StringUtils.firstNonBlank(null, null, null)     = null
     * StringUtils.firstNonBlank(null, "", " ")        = null
     * StringUtils.firstNonBlank("abc")                = "abc"
     * StringUtils.firstNonBlank(null, "xyz")          = "xyz"
     * StringUtils.firstNonBlank(null, "", " ", "xyz") = "xyz"
     * StringUtils.firstNonBlank(null, "xyz", "abc")   = "xyz"
     * StringUtils.firstNonBlank()                     = null
    </pre> *
     *
     * @param <T> the specific kind of CharSequence
     * @param values  the values to test, may be `null` or empty
     * @return the first value from `values` which is not blank,
     * or `null` if there are no non-blank values
     * @since 3.8
    </T> */
    @SafeVarargs
    fun <T : CharSequence?> firstNonBlank(vararg values: T): T? {
        if (values != null) {
            for (`val` in values) {
                if (isNotBlank(`val`)) {
                    return `val`
                }
            }
        }
        return null
    }

    /**
     *
     * Returns the first value in the array which is not empty.
     *
     *
     * If all values are empty or the array is `null`
     * or empty then `null` is returned.
     *
     * <pre>
     * StringUtils.firstNonEmpty(null, null, null)   = null
     * StringUtils.firstNonEmpty(null, null, "")     = null
     * StringUtils.firstNonEmpty(null, "", " ")      = " "
     * StringUtils.firstNonEmpty("abc")              = "abc"
     * StringUtils.firstNonEmpty(null, "xyz")        = "xyz"
     * StringUtils.firstNonEmpty("", "xyz")          = "xyz"
     * StringUtils.firstNonEmpty(null, "xyz", "abc") = "xyz"
     * StringUtils.firstNonEmpty()                   = null
    </pre> *
     *
     * @param <T> the specific kind of CharSequence
     * @param values  the values to test, may be `null` or empty
     * @return the first value from `values` which is not empty,
     * or `null` if there are no non-empty values
     * @since 3.8
    </T> */
    @SafeVarargs
    fun <T : CharSequence?> firstNonEmpty(vararg values: T): T? {
        if (values != null) {
            for (`val` in values) {
                if (isNotEmpty(`val`)) {
                    return `val`
                }
            }
        }
        return null
    }

    /**
     *
     * Returns either the passed in CharSequence, or if the CharSequence is
     * whitespace, empty ("") or `null`, the value of `defaultStr`.
     *
     *
     * Whitespace is defined by [Character.isWhitespace].
     *
     * <pre>
     * StringUtils.defaultIfBlank(null, "NULL")  = "NULL"
     * StringUtils.defaultIfBlank("", "NULL")    = "NULL"
     * StringUtils.defaultIfBlank(" ", "NULL")   = "NULL"
     * StringUtils.defaultIfBlank("bat", "NULL") = "bat"
     * StringUtils.defaultIfBlank("", null)      = null
    </pre> *
     * @param <T> the specific kind of CharSequence
     * @param str the CharSequence to check, may be null
     * @param defaultStr  the default CharSequence to return
     * if the input is whitespace, empty ("") or `null`, may be null
     * @return the passed in CharSequence, or the default
     * @see StringUtils.defaultString
    </T> */
    fun <T : CharSequence?> defaultIfBlank(str: T, defaultStr: T): T {
        return if (isBlank(str)) defaultStr else str
    }

    /**
     *
     * Returns either the passed in CharSequence, or if the CharSequence is
     * empty or `null`, the value of `defaultStr`.
     *
     * <pre>
     * StringUtils.defaultIfEmpty(null, "NULL")  = "NULL"
     * StringUtils.defaultIfEmpty("", "NULL")    = "NULL"
     * StringUtils.defaultIfEmpty(" ", "NULL")   = " "
     * StringUtils.defaultIfEmpty("bat", "NULL") = "bat"
     * StringUtils.defaultIfEmpty("", null)      = null
    </pre> *
     * @param <T> the specific kind of CharSequence
     * @param str  the CharSequence to check, may be null
     * @param defaultStr  the default CharSequence to return
     * if the input is empty ("") or `null`, may be null
     * @return the passed in CharSequence, or the default
     * @see StringUtils.defaultString
    </T> */
    fun <T : CharSequence?> defaultIfEmpty(str: T, defaultStr: T): T {
        return if (isEmpty(str)) defaultStr else str
    }
    // Rotating (circular shift)
//-----------------------------------------------------------------------
    /**
     *
     * Rotate (circular shift) a String of `shift` characters.
     *
     *  * If `shift > 0`, right circular shift (ex : ABCDEF =&gt; FABCDE)
     *  * If `shift < 0`, left circular shift (ex : ABCDEF =&gt; BCDEFA)
     *
     *
     * <pre>
     * StringUtils.rotate(null, *)        = null
     * StringUtils.rotate("", *)          = ""
     * StringUtils.rotate("abcdefg", 0)   = "abcdefg"
     * StringUtils.rotate("abcdefg", 2)   = "fgabcde"
     * StringUtils.rotate("abcdefg", -2)  = "cdefgab"
     * StringUtils.rotate("abcdefg", 7)   = "abcdefg"
     * StringUtils.rotate("abcdefg", -7)  = "abcdefg"
     * StringUtils.rotate("abcdefg", 9)   = "fgabcde"
     * StringUtils.rotate("abcdefg", -9)  = "cdefgab"
    </pre> *
     *
     * @param str  the String to rotate, may be null
     * @param shift  number of time to shift (positive : right shift, negative : left shift)
     * @return the rotated String,
     * or the original String if `shift == 0`,
     * or `null` if null String input
     * @since 3.5
     */
    fun rotate(str: String?, shift: Int): String? {
        if (str == null) {
            return null
        }
        val strLen = str.length
        if (shift == 0 || strLen == 0 || shift % strLen == 0) {
            return str
        }
        val builder = StringBuilder(strLen)
        val offset = -(shift % strLen)
        builder.append(substring(str, offset))
        builder.append(substring(str, 0, offset))
        return builder.toString()
    }
    // Reversing
//-----------------------------------------------------------------------
    /**
     *
     * Reverses a String as per [StringBuilder.reverse].
     *
     *
     * A `null` String returns `null`.
     *
     * <pre>
     * StringUtils.reverse(null)  = null
     * StringUtils.reverse("")    = ""
     * StringUtils.reverse("bat") = "tab"
    </pre> *
     *
     * @param str  the String to reverse, may be null
     * @return the reversed String, `null` if null String input
     */
    fun reverse(str: String?): String? {
        return if (str == null) {
            null
        } else StringBuilder(str).reverse().toString()
    }

    /**
     *
     * Reverses a String that is delimited by a specific character.
     *
     *
     * The Strings between the delimiters are not reversed.
     * Thus java.lang.String becomes String.lang.java (if the delimiter
     * is `'.'`).
     *
     * <pre>
     * StringUtils.reverseDelimited(null, *)      = null
     * StringUtils.reverseDelimited("", *)        = ""
     * StringUtils.reverseDelimited("a.b.c", 'x') = "a.b.c"
     * StringUtils.reverseDelimited("a.b.c", ".") = "c.b.a"
    </pre> *
     *
     * @param str  the String to reverse, may be null
     * @param separatorChar  the separator character to use
     * @return the reversed String, `null` if null String input
     * @since 2.0
     */
    fun reverseDelimited(str: String?, separatorChar: Char): String? {
        if (str == null) {
            return null
        }
        // could implement manually, but simple way is to reuse other,
// probably slower, methods.
        val strs = split(str, separatorChar)
        ArrayUtils.reverse(strs)
        return join(strs, separatorChar)
    }
    // Abbreviating
//-----------------------------------------------------------------------
    /**
     *
     * Abbreviates a String using ellipses. This will turn
     * "Now is the time for all good men" into "Now is the time for..."
     *
     *
     * Specifically:
     *
     *  * If the number of characters in `str` is less than or equal to
     * `maxWidth`, return `str`.
     *  * Else abbreviate it to `(substring(str, 0, max-3) + "...")`.
     *  * If `maxWidth` is less than `4`, throw an
     * `IllegalArgumentException`.
     *  * In no case will it return a String of length greater than
     * `maxWidth`.
     *
     *
     * <pre>
     * StringUtils.abbreviate(null, *)      = null
     * StringUtils.abbreviate("", 4)        = ""
     * StringUtils.abbreviate("abcdefg", 6) = "abc..."
     * StringUtils.abbreviate("abcdefg", 7) = "abcdefg"
     * StringUtils.abbreviate("abcdefg", 8) = "abcdefg"
     * StringUtils.abbreviate("abcdefg", 4) = "a..."
     * StringUtils.abbreviate("abcdefg", 3) = IllegalArgumentException
    </pre> *
     *
     * @param str  the String to check, may be null
     * @param maxWidth  maximum length of result String, must be at least 4
     * @return abbreviated String, `null` if null String input
     * @throws IllegalArgumentException if the width is too small
     * @since 2.0
     */
    fun abbreviate(str: String, maxWidth: Int): String {
        val defaultAbbrevMarker = "..."
        return abbreviate(str, defaultAbbrevMarker, 0, maxWidth)
    }

    /**
     *
     * Abbreviates a String using ellipses. This will turn
     * "Now is the time for all good men" into "...is the time for..."
     *
     *
     * Works like `abbreviate(String, int)`, but allows you to specify
     * a "left edge" offset.  Note that this left edge is not necessarily going to
     * be the leftmost character in the result, or the first character following the
     * ellipses, but it will appear somewhere in the result.
     *
     *
     * In no case will it return a String of length greater than
     * `maxWidth`.
     *
     * <pre>
     * StringUtils.abbreviate(null, *, *)                = null
     * StringUtils.abbreviate("", 0, 4)                  = ""
     * StringUtils.abbreviate("abcdefghijklmno", -1, 10) = "abcdefg..."
     * StringUtils.abbreviate("abcdefghijklmno", 0, 10)  = "abcdefg..."
     * StringUtils.abbreviate("abcdefghijklmno", 1, 10)  = "abcdefg..."
     * StringUtils.abbreviate("abcdefghijklmno", 4, 10)  = "abcdefg..."
     * StringUtils.abbreviate("abcdefghijklmno", 5, 10)  = "...fghi..."
     * StringUtils.abbreviate("abcdefghijklmno", 6, 10)  = "...ghij..."
     * StringUtils.abbreviate("abcdefghijklmno", 8, 10)  = "...ijklmno"
     * StringUtils.abbreviate("abcdefghijklmno", 10, 10) = "...ijklmno"
     * StringUtils.abbreviate("abcdefghijklmno", 12, 10) = "...ijklmno"
     * StringUtils.abbreviate("abcdefghij", 0, 3)        = IllegalArgumentException
     * StringUtils.abbreviate("abcdefghij", 5, 6)        = IllegalArgumentException
    </pre> *
     *
     * @param str  the String to check, may be null
     * @param offset  left edge of source String
     * @param maxWidth  maximum length of result String, must be at least 4
     * @return abbreviated String, `null` if null String input
     * @throws IllegalArgumentException if the width is too small
     * @since 2.0
     */
    fun abbreviate(str: String, offset: Int, maxWidth: Int): String {
        val defaultAbbrevMarker = "..."
        return abbreviate(str, defaultAbbrevMarker, offset, maxWidth)
    }

    /**
     *
     * Abbreviates a String using another given String as replacement marker. This will turn
     * "Now is the time for all good men" into "Now is the time for..." if "..." was defined
     * as the replacement marker.
     *
     *
     * Specifically:
     *
     *  * If the number of characters in `str` is less than or equal to
     * `maxWidth`, return `str`.
     *  * Else abbreviate it to `(substring(str, 0, max-abbrevMarker.length) + abbrevMarker)`.
     *  * If `maxWidth` is less than `abbrevMarker.length + 1`, throw an
     * `IllegalArgumentException`.
     *  * In no case will it return a String of length greater than
     * `maxWidth`.
     *
     *
     * <pre>
     * StringUtils.abbreviate(null, "...", *)      = null
     * StringUtils.abbreviate("abcdefg", null, *)  = "abcdefg"
     * StringUtils.abbreviate("", "...", 4)        = ""
     * StringUtils.abbreviate("abcdefg", ".", 5)   = "abcd."
     * StringUtils.abbreviate("abcdefg", ".", 7)   = "abcdefg"
     * StringUtils.abbreviate("abcdefg", ".", 8)   = "abcdefg"
     * StringUtils.abbreviate("abcdefg", "..", 4)  = "ab.."
     * StringUtils.abbreviate("abcdefg", "..", 3)  = "a.."
     * StringUtils.abbreviate("abcdefg", "..", 2)  = IllegalArgumentException
     * StringUtils.abbreviate("abcdefg", "...", 3) = IllegalArgumentException
    </pre> *
     *
     * @param str  the String to check, may be null
     * @param abbrevMarker  the String used as replacement marker
     * @param maxWidth  maximum length of result String, must be at least `abbrevMarker.length + 1`
     * @return abbreviated String, `null` if null String input
     * @throws IllegalArgumentException if the width is too small
     * @since 3.6
     */
    fun abbreviate(str: String, abbrevMarker: String, maxWidth: Int): String {
        return abbreviate(str, abbrevMarker, 0, maxWidth)
    }

    /**
     *
     * Abbreviates a String using a given replacement marker. This will turn
     * "Now is the time for all good men" into "...is the time for..." if "..." was defined
     * as the replacement marker.
     *
     *
     * Works like `abbreviate(String, String, int)`, but allows you to specify
     * a "left edge" offset.  Note that this left edge is not necessarily going to
     * be the leftmost character in the result, or the first character following the
     * replacement marker, but it will appear somewhere in the result.
     *
     *
     * In no case will it return a String of length greater than `maxWidth`.
     *
     * <pre>
     * StringUtils.abbreviate(null, null, *, *)                 = null
     * StringUtils.abbreviate("abcdefghijklmno", null, *, *)    = "abcdefghijklmno"
     * StringUtils.abbreviate("", "...", 0, 4)                  = ""
     * StringUtils.abbreviate("abcdefghijklmno", "---", -1, 10) = "abcdefg---"
     * StringUtils.abbreviate("abcdefghijklmno", ",", 0, 10)    = "abcdefghi,"
     * StringUtils.abbreviate("abcdefghijklmno", ",", 1, 10)    = "abcdefghi,"
     * StringUtils.abbreviate("abcdefghijklmno", ",", 2, 10)    = "abcdefghi,"
     * StringUtils.abbreviate("abcdefghijklmno", "::", 4, 10)   = "::efghij::"
     * StringUtils.abbreviate("abcdefghijklmno", "...", 6, 10)  = "...ghij..."
     * StringUtils.abbreviate("abcdefghijklmno", "*", 9, 10)    = "*ghijklmno"
     * StringUtils.abbreviate("abcdefghijklmno", "'", 10, 10)   = "'ghijklmno"
     * StringUtils.abbreviate("abcdefghijklmno", "!", 12, 10)   = "!ghijklmno"
     * StringUtils.abbreviate("abcdefghij", "abra", 0, 4)       = IllegalArgumentException
     * StringUtils.abbreviate("abcdefghij", "...", 5, 6)        = IllegalArgumentException
    </pre> *
     *
     * @param str  the String to check, may be null
     * @param abbrevMarker  the String used as replacement marker
     * @param offset  left edge of source String
     * @param maxWidth  maximum length of result String, must be at least 4
     * @return abbreviated String, `null` if null String input
     * @throws IllegalArgumentException if the width is too small
     * @since 3.6
     */
    fun abbreviate(str: String, abbrevMarker: String, offset: Int, maxWidth: Int): String {
        var offset = offset
        if (isEmpty(str) || isEmpty(abbrevMarker)) {
            return str
        }
        val abbrevMarkerLength = abbrevMarker.length
        val minAbbrevWidth = abbrevMarkerLength + 1
        val minAbbrevWidthOffset = abbrevMarkerLength + abbrevMarkerLength + 1
        require(maxWidth >= minAbbrevWidth) { String.format("Minimum abbreviation width is %d", minAbbrevWidth) }
        if (str.length <= maxWidth) {
            return str
        }
        if (offset > str.length) {
            offset = str.length
        }
        if (str.length - offset < maxWidth - abbrevMarkerLength) {
            offset = str.length - (maxWidth - abbrevMarkerLength)
        }
        if (offset <= abbrevMarkerLength + 1) {
            return str.substring(0, maxWidth - abbrevMarkerLength) + abbrevMarker
        }
        require(maxWidth >= minAbbrevWidthOffset) { String.format("Minimum abbreviation width with offset is %d", minAbbrevWidthOffset) }
        return if (offset + maxWidth - abbrevMarkerLength < str.length) {
            abbrevMarker + abbreviate(
                str.substring(offset),
                abbrevMarker,
                maxWidth - abbrevMarkerLength
            )
        } else abbrevMarker + str.substring(str.length - (maxWidth - abbrevMarkerLength))
    }

    /**
     *
     * Abbreviates a String to the length passed, replacing the middle characters with the supplied
     * replacement String.
     *
     *
     * This abbreviation only occurs if the following criteria is met:
     *
     *  * Neither the String for abbreviation nor the replacement String are null or empty
     *  * The length to truncate to is less than the length of the supplied String
     *  * The length to truncate to is greater than 0
     *  * The abbreviated String will have enough room for the length supplied replacement String
     * and the first and last characters of the supplied String for abbreviation
     *
     *
     * Otherwise, the returned String will be the same as the supplied String for abbreviation.
     *
     *
     * <pre>
     * StringUtils.abbreviateMiddle(null, null, 0)      = null
     * StringUtils.abbreviateMiddle("abc", null, 0)      = "abc"
     * StringUtils.abbreviateMiddle("abc", ".", 0)      = "abc"
     * StringUtils.abbreviateMiddle("abc", ".", 3)      = "abc"
     * StringUtils.abbreviateMiddle("abcdef", ".", 4)     = "ab.f"
    </pre> *
     *
     * @param str  the String to abbreviate, may be null
     * @param middle the String to replace the middle characters with, may be null
     * @param length the length to abbreviate `str` to.
     * @return the abbreviated String if the above criteria is met, or the original String supplied for abbreviation.
     * @since 2.5
     */
    fun abbreviateMiddle(str: String, middle: String, length: Int): String {
        if (isEmpty(str) || isEmpty(middle)) {
            return str
        }
        if (length >= str.length || length < middle.length + 2) {
            return str
        }
        val targetSting = length - middle.length
        val startOffset = targetSting / 2 + targetSting % 2
        val endOffset = str.length - targetSting / 2
        return str.substring(0, startOffset) +
                middle +
                str.substring(endOffset)
    }
    // Difference
//-----------------------------------------------------------------------
    /**
     *
     * Compares two Strings, and returns the portion where they differ.
     * More precisely, return the remainder of the second String,
     * starting from where it's different from the first. This means that
     * the difference between "abc" and "ab" is the empty String and not "c".
     *
     *
     * For example,
     * `difference("i am a machine", "i am a robot") -> "robot"`.
     *
     * <pre>
     * StringUtils.difference(null, null) = null
     * StringUtils.difference("", "") = ""
     * StringUtils.difference("", "abc") = "abc"
     * StringUtils.difference("abc", "") = ""
     * StringUtils.difference("abc", "abc") = ""
     * StringUtils.difference("abc", "ab") = ""
     * StringUtils.difference("ab", "abxyz") = "xyz"
     * StringUtils.difference("abcde", "abxyz") = "xyz"
     * StringUtils.difference("abcde", "xyz") = "xyz"
    </pre> *
     *
     * @param str1  the first String, may be null
     * @param str2  the second String, may be null
     * @return the portion of str2 where it differs from str1; returns the
     * empty String if they are equal
     * @see .indexOfDifference
     * @since 2.0
     */
    fun difference(str1: String?, str2: String?): String? {
        if (str1 == null) {
            return str2
        }
        if (str2 == null) {
            return str1
        }
        val at = indexOfDifference(str1, str2)
        return if (at == INDEX_NOT_FOUND) {
            EMPTY
        } else str2.substring(at)
    }

    /**
     *
     * Compares two CharSequences, and returns the index at which the
     * CharSequences begin to differ.
     *
     *
     * For example,
     * `indexOfDifference("i am a machine", "i am a robot") -> 7`
     *
     * <pre>
     * StringUtils.indexOfDifference(null, null) = -1
     * StringUtils.indexOfDifference("", "") = -1
     * StringUtils.indexOfDifference("", "abc") = 0
     * StringUtils.indexOfDifference("abc", "") = 0
     * StringUtils.indexOfDifference("abc", "abc") = -1
     * StringUtils.indexOfDifference("ab", "abxyz") = 2
     * StringUtils.indexOfDifference("abcde", "abxyz") = 2
     * StringUtils.indexOfDifference("abcde", "xyz") = 0
    </pre> *
     *
     * @param cs1  the first CharSequence, may be null
     * @param cs2  the second CharSequence, may be null
     * @return the index where cs1 and cs2 begin to differ; -1 if they are equal
     * @since 2.0
     * @since 3.0 Changed signature from indexOfDifference(String, String) to
     * indexOfDifference(CharSequence, CharSequence)
     */
    fun indexOfDifference(cs1: CharSequence?, cs2: CharSequence?): Int {
        if (cs1 === cs2) {
            return INDEX_NOT_FOUND
        }
        if (cs1 == null || cs2 == null) {
            return 0
        }
        var i: Int
        i = 0
        while (i < cs1.length && i < cs2.length) {
            if (cs1[i] != cs2[i]) {
                break
            }
            ++i
        }
        return if (i < cs2.length || i < cs1.length) {
            i
        } else INDEX_NOT_FOUND
    }

    /**
     *
     * Compares all CharSequences in an array and returns the index at which the
     * CharSequences begin to differ.
     *
     *
     * For example,
     * `indexOfDifference(new String[] {"i am a machine", "i am a robot"}) -> 7`
     *
     * <pre>
     * StringUtils.indexOfDifference(null) = -1
     * StringUtils.indexOfDifference(new String[] {}) = -1
     * StringUtils.indexOfDifference(new String[] {"abc"}) = -1
     * StringUtils.indexOfDifference(new String[] {null, null}) = -1
     * StringUtils.indexOfDifference(new String[] {"", ""}) = -1
     * StringUtils.indexOfDifference(new String[] {"", null}) = 0
     * StringUtils.indexOfDifference(new String[] {"abc", null, null}) = 0
     * StringUtils.indexOfDifference(new String[] {null, null, "abc"}) = 0
     * StringUtils.indexOfDifference(new String[] {"", "abc"}) = 0
     * StringUtils.indexOfDifference(new String[] {"abc", ""}) = 0
     * StringUtils.indexOfDifference(new String[] {"abc", "abc"}) = -1
     * StringUtils.indexOfDifference(new String[] {"abc", "a"}) = 1
     * StringUtils.indexOfDifference(new String[] {"ab", "abxyz"}) = 2
     * StringUtils.indexOfDifference(new String[] {"abcde", "abxyz"}) = 2
     * StringUtils.indexOfDifference(new String[] {"abcde", "xyz"}) = 0
     * StringUtils.indexOfDifference(new String[] {"xyz", "abcde"}) = 0
     * StringUtils.indexOfDifference(new String[] {"i am a machine", "i am a robot"}) = 7
    </pre> *
     *
     * @param css  array of CharSequences, entries may be null
     * @return the index where the strings begin to differ; -1 if they are all equal
     * @since 2.4
     * @since 3.0 Changed signature from indexOfDifference(String...) to indexOfDifference(CharSequence...)
     */
    fun indexOfDifference(vararg css: CharSequence): Int {
        if (css == null || css.size <= 1) {
            return INDEX_NOT_FOUND
        }
        var anyStringNull = false
        var allStringsNull = true
        val arrayLen = css.size
        var shortestStrLen = Int.MAX_VALUE
        var longestStrLen = 0
        // find the min and max string lengths; this avoids checking to make
// sure we are not exceeding the length of the string each time through
// the bottom loop.
        for (cs in css) {
            if (cs == null) {
                anyStringNull = true
                shortestStrLen = 0
            } else {
                allStringsNull = false
                shortestStrLen = Math.min(cs.length, shortestStrLen)
                longestStrLen = Math.max(cs.length, longestStrLen)
            }
        }
        // handle lists containing all nulls or all empty strings
        if (allStringsNull || longestStrLen == 0 && !anyStringNull) {
            return INDEX_NOT_FOUND
        }
        // handle lists containing some nulls or some empty strings
        if (shortestStrLen == 0) {
            return 0
        }
        // find the position with the first difference across all strings
        var firstDiff = -1
        for (stringPos in 0 until shortestStrLen) {
            val comparisonChar = css[0][stringPos]
            for (arrayPos in 1 until arrayLen) {
                if (css[arrayPos][stringPos] != comparisonChar) {
                    firstDiff = stringPos
                    break
                }
            }
            if (firstDiff != -1) {
                break
            }
        }
        return if (firstDiff == -1 && shortestStrLen != longestStrLen) { // we compared all of the characters up to the length of the
// shortest string and didn't find a match, but the string lengths
// vary, so return the length of the shortest string.
            shortestStrLen
        } else firstDiff
    }

    /**
     *
     * Compares all Strings in an array and returns the initial sequence of
     * characters that is common to all of them.
     *
     *
     * For example,
     * `getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) -> "i am a "`
     *
     * <pre>
     * StringUtils.getCommonPrefix(null) = ""
     * StringUtils.getCommonPrefix(new String[] {}) = ""
     * StringUtils.getCommonPrefix(new String[] {"abc"}) = "abc"
     * StringUtils.getCommonPrefix(new String[] {null, null}) = ""
     * StringUtils.getCommonPrefix(new String[] {"", ""}) = ""
     * StringUtils.getCommonPrefix(new String[] {"", null}) = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", null, null}) = ""
     * StringUtils.getCommonPrefix(new String[] {null, null, "abc"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"", "abc"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", ""}) = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", "abc"}) = "abc"
     * StringUtils.getCommonPrefix(new String[] {"abc", "a"}) = "a"
     * StringUtils.getCommonPrefix(new String[] {"ab", "abxyz"}) = "ab"
     * StringUtils.getCommonPrefix(new String[] {"abcde", "abxyz"}) = "ab"
     * StringUtils.getCommonPrefix(new String[] {"abcde", "xyz"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"xyz", "abcde"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) = "i am a "
    </pre> *
     *
     * @param strs  array of String objects, entries may be null
     * @return the initial sequence of characters that are common to all Strings
     * in the array; empty String if the array is null, the elements are all null
     * or if there is no common prefix.
     * @since 2.4
     */
    fun getCommonPrefix(vararg strs: String?): String? {
        if (strs == null || strs.size == 0) {
            return EMPTY
        }
        val smallestIndexOfDiff = indexOfDifference(*strs)
        return if (smallestIndexOfDiff == INDEX_NOT_FOUND) { // all strings were identical
            if (strs[0] == null) {
                EMPTY
            } else strs[0]
        } else if (smallestIndexOfDiff == 0) { // there were no common initial characters
            EMPTY
        } else { // we found a common initial character sequence
            strs[0]!!.substring(0, smallestIndexOfDiff)
        }
    }
    // Misc
//-----------------------------------------------------------------------
    /**
     *
     * Find the Levenshtein distance between two Strings.
     *
     *
     * This is the number of changes needed to change one String into
     * another, where each change is a single character modification (deletion,
     * insertion or substitution).
     *
     *
     * The implementation uses a single-dimensional array of length s.length() + 1. See
     * [
 * http://blog.softwx.net/2014/12/optimizing-levenshtein-algorithm-in-c.html](http://blog.softwx.net/2014/12/optimizing-levenshtein-algorithm-in-c.html) for details.
     *
     * <pre>
     * StringUtils.getLevenshteinDistance(null, *)             = IllegalArgumentException
     * StringUtils.getLevenshteinDistance(*, null)             = IllegalArgumentException
     * StringUtils.getLevenshteinDistance("", "")              = 0
     * StringUtils.getLevenshteinDistance("", "a")             = 1
     * StringUtils.getLevenshteinDistance("aaapppp", "")       = 7
     * StringUtils.getLevenshteinDistance("frog", "fog")       = 1
     * StringUtils.getLevenshteinDistance("fly", "ant")        = 3
     * StringUtils.getLevenshteinDistance("elephant", "hippo") = 7
     * StringUtils.getLevenshteinDistance("hippo", "elephant") = 7
     * StringUtils.getLevenshteinDistance("hippo", "zzzzzzzz") = 8
     * StringUtils.getLevenshteinDistance("hello", "hallo")    = 1
    </pre> *
     *
     * @param s  the first String, must not be null
     * @param t  the second String, must not be null
     * @return result distance
     * @throws IllegalArgumentException if either String input `null`
     * @since 3.0 Changed signature from getLevenshteinDistance(String, String) to
     * getLevenshteinDistance(CharSequence, CharSequence)
     */
    @Deprecated(
        "as of 3.6, use commons-text\n" + "      <a href=" https ://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/similarity/LevenshteinDistance.html">\n" + "      LevenshteinDistance</a> instead")    fun /*@@jyakws@@*/getLevenshteinDistance(  s:/*@@fwocqv@@*/CharSequence?,   t:/*@@fwocqv@@*/CharSequence?): /*@@fplarc@@*/Int{
        var s: CharSequence? = s
    var t = t
    if (s == null || t == null)
    {
        throw IllegalArgumentException("Strings must not be null")
    }

    var n: Int = s.length
    var m: Int = t.length
    if (n == 0)
    {
        return m
    } else if (m == 0)
    {
        return n
    }
    if (n > m)
    { // swap the input strings to consume less memory
        val tmp: CharSequence = s
        s = t
        t = tmp
        n = m
        m = t.length
    }

    val p = IntArray(n + 1)
    // indexes into strings s and t
    var i: Int // iterates through s
    var j: Int // iterates through t
    var upper_left: Int
    var upper: Int
    var t_j: Char // jth character of t
    var cost: Int
    i = 0
    while (i <= n)
    {
        p[i] = i
        i++
    }
    j = 1
    while (j <= m)
    {
        upper_left = p[0]
        t_j = t.get(j - 1)
        p[0] = j
        i = 1
        while (i <= n) {
            upper = p[i]
            cost = if (s.get(i - 1) == t_j) 0 else 1
            // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
            p[i] = Math.min(Math.min(p[i - 1] + 1, p[i] + 1), upper_left + cost)
            upper_left = upper
            i++
        }
        j++
    }
    return p.get(n)
}
/**
 *
 * Find the Levenshtein distance between two Strings if it's less than or equal to a given
 * threshold.
 *
 *
 * This is the number of changes needed to change one String into
 * another, where each change is a single character modification (deletion,
 * insertion or substitution).
 *
 *
 * This implementation follows from Algorithms on Strings, Trees and Sequences by Dan Gusfield
 * and Chas Emerick's implementation of the Levenshtein distance algorithm from
 * [http://www.merriampark.com/ld.htm](http://www.merriampark.com/ld.htm)
 *
 * <pre>
 * StringUtils.getLevenshteinDistance(null, *, *)             = IllegalArgumentException
 * StringUtils.getLevenshteinDistance(*, null, *)             = IllegalArgumentException
 * StringUtils.getLevenshteinDistance(*, *, -1)               = IllegalArgumentException
 * StringUtils.getLevenshteinDistance("", "", 0)              = 0
 * StringUtils.getLevenshteinDistance("aaapppp", "", 8)       = 7
 * StringUtils.getLevenshteinDistance("aaapppp", "", 7)       = 7
 * StringUtils.getLevenshteinDistance("aaapppp", "", 6))      = -1
 * StringUtils.getLevenshteinDistance("elephant", "hippo", 7) = 7
 * StringUtils.getLevenshteinDistance("elephant", "hippo", 6) = -1
 * StringUtils.getLevenshteinDistance("hippo", "elephant", 7) = 7
 * StringUtils.getLevenshteinDistance("hippo", "elephant", 6) = -1
</pre> *
 *
 * @param s  the first String, must not be null
 * @param t  the second String, must not be null
 * @param threshold the target threshold, must not be negative
 * @return result distance, or `-1` if the distance would be greater than the threshold
 * @throws IllegalArgumentException if either String input `null` or negative threshold
 */
@Deprecated(
    "as of 3.6, use commons-text\n" + "      <a href=" https ://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/similarity/LevenshteinDistance.html">\n" + "      LevenshteinDistance</a> instead")    fun /*@@hiugwt@@*/getLevenshteinDistance(  s:/*@@fwocqv@@*/CharSequence?,   t:/*@@fwocqv@@*/CharSequence?,   threshold:/*@@fplarc@@*/Int): /*@@fplarc@@*/Int{
    var s: CharSequence? = s
var t = t
if (s == null || t == null){
    throw IllegalArgumentException("Strings must not be null")
}
if (threshold < 0){
    throw IllegalArgumentException("Threshold must not be negative")
}

/*
        This implementation only computes the distance if it's less than or equal to the
        threshold value, returning -1 if it's greater.  The advantage is performance: unbounded
        distance is O(nm), but a bound of k allows us to reduce it to O(km) time by only
        computing a diagonal stripe of width 2k + 1 of the cost table.
        It is also possible to use this to compute the unbounded Levenshtein distance by starting
        the threshold at 1 and doubling each time until the distance is found; this is O(dm), where
        d is the distance.

        One subtlety comes from needing to ignore entries on the border of our stripe
        eg.
        p[] = |#|#|#|*
        d[] =  *|#|#|#|
        We must ignore the entry to the left of the leftmost member
        We must ignore the entry above the rightmost member

        Another subtlety comes from our stripe running off the matrix if the strings aren't
        of the same size.  Since string s is always swapped to be the shorter of the two,
        the stripe will always run off to the upper right instead of the lower left of the matrix.

        As a concrete example, suppose s is of length 5, t is of length 7, and our threshold is 1.
        In this case we're going to walk a stripe of length 3.  The matrix would look like so:

           1 2 3 4 5
        1 |#|#| | | |
        2 |#|#|#| | |
        3 | |#|#|#| |
        4 | | |#|#|#|
        5 | | | |#|#|
        6 | | | | |#|
        7 | | | | | |

        Note how the stripe leads off the table as there is no possible way to turn a string of length 5
        into one of length 7 in edit distance of 1.

        Additionally, this implementation decreases memory usage by using two
        single-dimensional arrays and swapping them back and forth instead of allocating
        an entire n by m matrix.  This requires a few minor changes, such as immediately returning
        when it's detected that the stripe has run off the matrix and initially filling the arrays with
        large values so that entries we don't compute are ignored.

        See Algorithms on Strings, Trees and Sequences by Dan Gusfield for some discussion.
         */  var n: Int = s.length // length of s
var m: Int = t.length // length of t
// if one string is empty, the edit distance is necessarily the length of the other
if (n == 0){
    return if (m <= threshold) m else -1
} else if (m == 0){
    return if (n <= threshold) n else -1
} else if (java.lang.Math.abs(n - m) > threshold){ // no need to calculate the distance if the length difference is greater than the threshold
    return -1
}
if (n > m){ // swap the two strings to consume less memory
    val tmp: CharSequence = s
    s = t
    t = tmp
    n = m
    m = t.length
}

var p = IntArray(n + 1) // 'previous' cost array, horizontally
var d = IntArray(n + 1) // cost array, horizontally
var _d: IntArray // placeholder to assist in swapping p and d
// fill in starting table values
val boundary = Math.min(n, threshold) + 1
for (i in 0 until boundary) {
    p[i] = i
}
// these fills ensure that the value above the rightmost entry of our
// stripe will be ignored in following loop iterations
Arrays.fill(p, boundary, p.size, Int.Companion.MAX_VALUE)
Arrays.fill(d, Int.Companion.MAX_VALUE)
// iterates through t
for (j in 1 .. m) {
    val t_j: Char = t.get((j - 1).toInt()) // jth character of t
    d[0] = j.toInt()
    // compute stripe indices, constrain to array size
    val min = Math.max(1, j - threshold)
    val max = if (j > Int.MAX_VALUE - threshold) n else Math.min(n, j + threshold)
    // the stripe may lead off of the table if s and t are of different sizes
    if (min > max) {
        return -1
    }
    // ignore entry left of leftmost
    if (min > 1) {
        d[min - 1] = Int.MAX_VALUE
    }
    // iterates through [min, max] in s
    for (i in min..max) {
        if (s.get(i - 1) == t_j) { // diagonally left and up
            d[i] = p[i - 1]
        } else { // 1 + minimum of cell to the left, to the top, diagonally left and up
            d[i] = 1 + Math.min(Math.min(d[i - 1], p[i]), p[i - 1])
        }
    }
    // copy current distance counts to 'previous row' distance counts
    _d = p
    p = d
    d = _d
}
// if p[n] is greater than the threshold, there's no guarantee on it being the correct
// distance
if (p.get(n) <= threshold){
    return p[n]
}
return -1
}
/**
 *
 * Find the Jaro Winkler Distance which indicates the similarity score between two Strings.
 *
 *
 * The Jaro measure is the weighted sum of percentage of matched characters from each file and transposed characters.
 * Winkler increased this measure for matching initial characters.
 *
 *
 * This implementation is based on the Jaro Winkler similarity algorithm
 * from [http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance](http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance).
 *
 * <pre>
 * StringUtils.getJaroWinklerDistance(null, null)          = IllegalArgumentException
 * StringUtils.getJaroWinklerDistance("", "")              = 0.0
 * StringUtils.getJaroWinklerDistance("", "a")             = 0.0
 * StringUtils.getJaroWinklerDistance("aaapppp", "")       = 0.0
 * StringUtils.getJaroWinklerDistance("frog", "fog")       = 0.93
 * StringUtils.getJaroWinklerDistance("fly", "ant")        = 0.0
 * StringUtils.getJaroWinklerDistance("elephant", "hippo") = 0.44
 * StringUtils.getJaroWinklerDistance("hippo", "elephant") = 0.44
 * StringUtils.getJaroWinklerDistance("hippo", "zzzzzzzz") = 0.0
 * StringUtils.getJaroWinklerDistance("hello", "hallo")    = 0.88
 * StringUtils.getJaroWinklerDistance("ABC Corporation", "ABC Corp") = 0.93
 * StringUtils.getJaroWinklerDistance("D N H Enterprises Inc", "D &amp; H Enterprises, Inc.") = 0.95
 * StringUtils.getJaroWinklerDistance("My Gym Children's Fitness Center", "My Gym. Childrens Fitness") = 0.92
 * StringUtils.getJaroWinklerDistance("PENNSYLVANIA", "PENNCISYLVNIA") = 0.88
</pre> *
 *
 * @param first the first String, must not be null
 * @param second the second String, must not be null
 * @return result distance
 * @throws IllegalArgumentException if either String input `null`
 * @since 3.3
 */
@Deprecated(
    "as of 3.6, use commons-text\n" + "      <a href=" https ://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/similarity/JaroWinklerDistance.html">\n" + "      JaroWinklerDistance</a> instead")    fun /*@@rpwawn@@*/getJaroWinklerDistance(  first:/*@@fwocqv@@*/CharSequence?,   second:/*@@fwocqv@@*/CharSequence?): /*@@pznbxz@@*/kotlin.Double{
    val DEFAULT_SCALING_FACTOR: kotlin.Double = 0.1
if (first == null || second == null){
    throw IllegalArgumentException("Strings must not be null")
}

val mtp: IntArray = StringUtils.matches(first, second)
val m = mtp[0].toDouble()
if (m == 0.0){
    return 0.0
}

val j: Double = (m / first.length + m / second.length + (m - mtp[1]) / m) / 3
val jw = if (j < 0.7) j else j + Math.min(DEFAULT_SCALING_FACTOR, 1.0 / mtp[3]) * mtp[2] * (1.0 - j)
return java.lang.Math.round(jw * 100.0) / 100.0
}
private fun matches(first: CharSequence, second: CharSequence): IntArray {
    val max: CharSequence
    val min: CharSequence
    if (first.length > second.length) {
        max = first
        min = second
    } else {
        max = second
        min = first
    }
    val range = Math.max(max.length / 2 - 1, 0)
    val matchIndexes = IntArray(min.length)
    Arrays.fill(matchIndexes, -1)
    val matchFlags = BooleanArray(max.length)
    var matches = 0
    for (mi in 0 until min.length) {
        val c1 = min[mi]
        var xi = Math.max(mi - range, 0)
        val xn = Math.min(mi + range + 1, max.length)
        while (xi < xn) {
            if (!matchFlags[xi] && c1 == max[xi]) {
                matchIndexes[mi] = xi
                matchFlags[xi] = true
                matches++
                break
            }
            xi++
        }
    }
    val ms1 = CharArray(matches)
    val ms2 = CharArray(matches)
    run {
        var i = 0
        var si = 0
        while (i < min.length) {
            if (matchIndexes[i] != -1) {
                ms1[si] = min[i]
                si++
            }
            i++
        }
    }
    var i = 0
    var si = 0
    while (i < max.length) {
        if (matchFlags[i]) {
            ms2[si] = max[i]
            si++
        }
        i++
    }
    var transpositions = 0
    for (mi in ms1.indices) {
        if (ms1[mi] != ms2[mi]) {
            transpositions++
        }
    }
    var prefix = 0
    for (mi in 0 until min.length) {
        if (first[mi] == second[mi]) {
            prefix++
        } else {
            break
        }
    }
    return intArrayOf(matches, transpositions / 2, prefix, max.length)
}
/**
 *
 * Find the Fuzzy Distance which indicates the similarity score between two Strings.
 *
 *
 * This string matching algorithm is similar to the algorithms of editors such as Sublime Text,
 * TextMate, Atom and others. One point is given for every matched character. Subsequent
 * matches yield two bonus points. A higher score indicates a higher similarity.
 *
 * <pre>
 * StringUtils.getFuzzyDistance(null, null, null)                                    = IllegalArgumentException
 * StringUtils.getFuzzyDistance("", "", Locale.ENGLISH)                              = 0
 * StringUtils.getFuzzyDistance("Workshop", "b", Locale.ENGLISH)                     = 0
 * StringUtils.getFuzzyDistance("Room", "o", Locale.ENGLISH)                         = 1
 * StringUtils.getFuzzyDistance("Workshop", "w", Locale.ENGLISH)                     = 1
 * StringUtils.getFuzzyDistance("Workshop", "ws", Locale.ENGLISH)                    = 2
 * StringUtils.getFuzzyDistance("Workshop", "wo", Locale.ENGLISH)                    = 4
 * StringUtils.getFuzzyDistance("Apache Software Foundation", "asf", Locale.ENGLISH) = 3
</pre> *
 *
 * @param term a full term that should be matched against, must not be null
 * @param query the query that will be matched against a term, must not be null
 * @param locale This string matching logic is case insensitive. A locale is necessary to normalize
 * both Strings to lower case.
 * @return result score
 * @throws IllegalArgumentException if either String input `null` or Locale input `null`
 * @since 3.4
 */
@Deprecated(
    "as of 3.6, use commons-text\n" + "      <a href=" https ://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/similarity/FuzzyScore.html">\n" + "      FuzzyScore</a> instead")    fun /*@@icpueu@@*/getFuzzyDistance(  term:/*@@fwocqv@@*/CharSequence?,   query:/*@@fwocqv@@*/CharSequence?,   locale:/*@@zsklzk@@*/java.util.Locale?): /*@@fplarc@@*/Int{
    if (term == null || query == null) {
        throw IllegalArgumentException("Strings must not be null")
    } else if (locale == null) {
        throw IllegalArgumentException("Locale must not be null")
    }
    // fuzzy logic is case insensitive. We normalize the Strings to lower
// case right from the start. Turning characters to lower case
// via Character.toLowerCase(char) is unfortunately insufficient
// as it does not accept a locale.
    val termLowerCase: kotlin.String? = term.toString().toLowerCase(locale)
val queryLowerCase: String = query.toString().toLowerCase(locale)
// the resulting score
var score = 0
// the position in the term which will be scanned next for potential
// query character matches
var termIndex = 0
// index of the previously matched character in the term
var previousMatchingCharacterIndex = Int.MIN_VALUE
for (queryIndex in 0 until queryLowerCase.length) {
    val queryChar = queryLowerCase[queryIndex]
    var termCharacterMatchFound = false
    while (termIndex < termLowerCase.length && !termCharacterMatchFound) {
        val termChar: Char = termLowerCase.get(termIndex)
        if (queryChar == termChar) { // simple character matches result in one point
            score++
            // subsequent character matches further improve
// the score.
            if (previousMatchingCharacterIndex + 1 == termIndex) {
                score += 2
            }
            previousMatchingCharacterIndex = termIndex
            // we can leave the nested loop. Every character in the
// query can match at most one character in the term.
            termCharacterMatchFound = true
        }
        termIndex++
    }
}
return score
}
// startsWith
//-----------------------------------------------------------------------
/**
 *
 * Check if a CharSequence starts with a specified prefix.
 *
 *
 * `null`s are handled without exceptions. Two `null`
 * references are considered to be equal. The comparison is case sensitive.
 *
 * <pre>
 * StringUtils.startsWith(null, null)      = true
 * StringUtils.startsWith(null, "abc")     = false
 * StringUtils.startsWith("abcdef", null)  = false
 * StringUtils.startsWith("abcdef", "abc") = true
 * StringUtils.startsWith("ABCDEF", "abc") = false
</pre> *
 *
 * @see String.startsWith
 * @param str  the CharSequence to check, may be null
 * @param prefix the prefix to find, may be null
 * @return `true` if the CharSequence starts with the prefix, case sensitive, or
 * both `null`
 * @since 2.4
 * @since 3.0 Changed signature from startsWith(String, String) to startsWith(CharSequence, CharSequence)
 */
fun startsWith(str: CharSequence?, prefix: CharSequence?): Boolean {
    return StringUtils.startsWith(str, prefix, false)
}

/**
 *
 * Case insensitive check if a CharSequence starts with a specified prefix.
 *
 *
 * `null`s are handled without exceptions. Two `null`
 * references are considered to be equal. The comparison is case insensitive.
 *
 * <pre>
 * StringUtils.startsWithIgnoreCase(null, null)      = true
 * StringUtils.startsWithIgnoreCase(null, "abc")     = false
 * StringUtils.startsWithIgnoreCase("abcdef", null)  = false
 * StringUtils.startsWithIgnoreCase("abcdef", "abc") = true
 * StringUtils.startsWithIgnoreCase("ABCDEF", "abc") = true
</pre> *
 *
 * @see String.startsWith
 * @param str  the CharSequence to check, may be null
 * @param prefix the prefix to find, may be null
 * @return `true` if the CharSequence starts with the prefix, case insensitive, or
 * both `null`
 * @since 2.4
 * @since 3.0 Changed signature from startsWithIgnoreCase(String, String) to startsWithIgnoreCase(CharSequence, CharSequence)
 */
fun startsWithIgnoreCase(str: CharSequence?, prefix: CharSequence?): Boolean {
    return StringUtils.startsWith(str, prefix, true)
}

/**
 *
 * Check if a CharSequence starts with a specified prefix (optionally case insensitive).
 *
 * @see String.startsWith
 * @param str  the CharSequence to check, may be null
 * @param prefix the prefix to find, may be null
 * @param ignoreCase indicates whether the compare should ignore case
 * (case insensitive) or not.
 * @return `true` if the CharSequence starts with the prefix or
 * both `null`
 */
private fun startsWith(str: CharSequence?, prefix: CharSequence?, ignoreCase: Boolean): Boolean {
    if (str == null || prefix == null) {
        return str === prefix
    }
    return if (prefix.length > str.length) {
        false
    } else CharSequenceUtils.regionMatches(str, ignoreCase, 0, prefix, 0, prefix.length)
}

/**
 *
 * Check if a CharSequence starts with any of the provided case-sensitive prefixes.
 *
 * <pre>
 * StringUtils.startsWithAny(null, null)      = false
 * StringUtils.startsWithAny(null, new String[] {"abc"})  = false
 * StringUtils.startsWithAny("abcxyz", null)     = false
 * StringUtils.startsWithAny("abcxyz", new String[] {""}) = true
 * StringUtils.startsWithAny("abcxyz", new String[] {"abc"}) = true
 * StringUtils.startsWithAny("abcxyz", new String[] {null, "xyz", "abc"}) = true
 * StringUtils.startsWithAny("abcxyz", null, "xyz", "ABCX") = false
 * StringUtils.startsWithAny("ABCXYZ", null, "xyz", "abc") = false
</pre> *
 *
 * @param sequence the CharSequence to check, may be null
 * @param searchStrings the case-sensitive CharSequence prefixes, may be empty or contain `null`
 * @see StringUtils.startsWith
 * @return `true` if the input `sequence` is `null` AND no `searchStrings` are provided, or
 * the input `sequence` begins with any of the provided case-sensitive `searchStrings`.
 * @since 2.5
 * @since 3.0 Changed signature from startsWithAny(String, String[]) to startsWithAny(CharSequence, CharSequence...)
 */
fun startsWithAny(sequence: CharSequence?, vararg searchStrings: CharSequence?): Boolean {
    if (StringUtils.isEmpty(sequence) || ArrayUtils.isEmpty(searchStrings)) {
        return false
    }
    for (searchString in searchStrings) {
        if (StringUtils.startsWith(sequence, searchString)) {
            return true
        }
    }
    return false
}
// endsWith
//-----------------------------------------------------------------------
/**
 *
 * Check if a CharSequence ends with a specified suffix.
 *
 *
 * `null`s are handled without exceptions. Two `null`
 * references are considered to be equal. The comparison is case sensitive.
 *
 * <pre>
 * StringUtils.endsWith(null, null)      = true
 * StringUtils.endsWith(null, "def")     = false
 * StringUtils.endsWith("abcdef", null)  = false
 * StringUtils.endsWith("abcdef", "def") = true
 * StringUtils.endsWith("ABCDEF", "def") = false
 * StringUtils.endsWith("ABCDEF", "cde") = false
 * StringUtils.endsWith("ABCDEF", "")    = true
</pre> *
 *
 * @see String.endsWith
 * @param str  the CharSequence to check, may be null
 * @param suffix the suffix to find, may be null
 * @return `true` if the CharSequence ends with the suffix, case sensitive, or
 * both `null`
 * @since 2.4
 * @since 3.0 Changed signature from endsWith(String, String) to endsWith(CharSequence, CharSequence)
 */
fun endsWith(str: CharSequence?, suffix: CharSequence?): Boolean {
    return StringUtils.endsWith(str, suffix, false)
}

/**
 *
 * Case insensitive check if a CharSequence ends with a specified suffix.
 *
 *
 * `null`s are handled without exceptions. Two `null`
 * references are considered to be equal. The comparison is case insensitive.
 *
 * <pre>
 * StringUtils.endsWithIgnoreCase(null, null)      = true
 * StringUtils.endsWithIgnoreCase(null, "def")     = false
 * StringUtils.endsWithIgnoreCase("abcdef", null)  = false
 * StringUtils.endsWithIgnoreCase("abcdef", "def") = true
 * StringUtils.endsWithIgnoreCase("ABCDEF", "def") = true
 * StringUtils.endsWithIgnoreCase("ABCDEF", "cde") = false
</pre> *
 *
 * @see String.endsWith
 * @param str  the CharSequence to check, may be null
 * @param suffix the suffix to find, may be null
 * @return `true` if the CharSequence ends with the suffix, case insensitive, or
 * both `null`
 * @since 2.4
 * @since 3.0 Changed signature from endsWithIgnoreCase(String, String) to endsWithIgnoreCase(CharSequence, CharSequence)
 */
fun endsWithIgnoreCase(str: CharSequence?, suffix: CharSequence?): Boolean {
    return StringUtils.endsWith(str, suffix, true)
}

/**
 *
 * Check if a CharSequence ends with a specified suffix (optionally case insensitive).
 *
 * @see String.endsWith
 * @param str  the CharSequence to check, may be null
 * @param suffix the suffix to find, may be null
 * @param ignoreCase indicates whether the compare should ignore case
 * (case insensitive) or not.
 * @return `true` if the CharSequence starts with the prefix or
 * both `null`
 */
private fun endsWith(str: CharSequence?, suffix: CharSequence?, ignoreCase: Boolean): Boolean {
    if (str == null || suffix == null) {
        return str === suffix
    }
    if (suffix.length > str.length) {
        return false
    }
    val strOffset = str.length - suffix.length
    return CharSequenceUtils.regionMatches(str, ignoreCase, strOffset, suffix, 0, suffix.length)
}

/**
 *
 *
 * Similar to [http://www.w3.org/TR/xpath/#function-normalize
 * -space](http://www.w3.org/TR/xpath/#function-normalize-space)
 *
 *
 *
 * The function returns the argument string with whitespace normalized by using
 * `[.trim]` to remove leading and trailing whitespace
 * and then replacing sequences of whitespace characters by a single space.
 *
 * In XML Whitespace characters are the same as those allowed by the [S](http://www.w3.org/TR/REC-xml/#NT-S) production, which is S ::= (#x20 | #x9 | #xD | #xA)+
 *
 *
 * Java's regexp pattern \s defines whitespace as [ \t\n\x0B\f\r]
 *
 *
 * For reference:
 *
 *  * \x0B = vertical tab
 *  * \f = #xC = form feed
 *  * #x20 = space
 *  * #x9 = \t
 *  * #xA = \n
 *  * #xD = \r
 *
 *
 *
 *
 * The difference is that Java's whitespace includes vertical tab and form feed, which this functional will also
 * normalize. Additionally `[.trim]` removes control characters (char &lt;= 32) from both
 * ends of this String.
 *
 *
 * @see Pattern
 *
 * @see .trim
 * @see [http://www.w3.org/TR/xpath/.function-normalize-space](http://www.w3.org/TR/xpath/.function-normalize-space)
 *
 * @param str the source String to normalize whitespaces from, may be null
 * @return the modified string with whitespace normalized, `null` if null String input
 *
 * @since 3.0
 */
fun normalizeSpace(str: String): String { // LANG-1020: Improved performance significantly by normalizing manually instead of using regex
// See https://github.com/librucha/commons-lang-normalizespaces-benchmark for performance test
    if (StringUtils.isEmpty(str)) {
        return str
    }
    val size = str.length
    val newChars = CharArray(size)
    var count = 0
    var whitespacesCount = 0
    var startWhitespaces = true
    for (i in 0 until size) {
        val actualChar = str[i]
        val isWhitespace = Character.isWhitespace(actualChar)
        if (isWhitespace) {
            if (whitespacesCount == 0 && !startWhitespaces) {
                newChars[count++] = StringUtils.SPACE[0]
            }
            whitespacesCount++
        } else {
            startWhitespaces = false
            newChars[count++] = if (actualChar.toInt() == 160) 32 else actualChar
            whitespacesCount = 0
        }
    }
    return if (startWhitespaces) {
        StringUtils.EMPTY
    } else String(newChars, 0, count - if (whitespacesCount > 0) 1 else 0).trim { it <= ' ' }
}

/**
 *
 * Check if a CharSequence ends with any of the provided case-sensitive suffixes.
 *
 * <pre>
 * StringUtils.endsWithAny(null, null)      = false
 * StringUtils.endsWithAny(null, new String[] {"abc"})  = false
 * StringUtils.endsWithAny("abcxyz", null)     = false
 * StringUtils.endsWithAny("abcxyz", new String[] {""}) = true
 * StringUtils.endsWithAny("abcxyz", new String[] {"xyz"}) = true
 * StringUtils.endsWithAny("abcxyz", new String[] {null, "xyz", "abc"}) = true
 * StringUtils.endsWithAny("abcXYZ", "def", "XYZ") = true
 * StringUtils.endsWithAny("abcXYZ", "def", "xyz") = false
</pre> *
 *
 * @param sequence  the CharSequence to check, may be null
 * @param searchStrings the case-sensitive CharSequences to find, may be empty or contain `null`
 * @see StringUtils.endsWith
 * @return `true` if the input `sequence` is `null` AND no `searchStrings` are provided, or
 * the input `sequence` ends in any of the provided case-sensitive `searchStrings`.
 * @since 3.0
 */
fun endsWithAny(sequence: CharSequence?, vararg searchStrings: CharSequence?): Boolean {
    if (StringUtils.isEmpty(sequence) || ArrayUtils.isEmpty(searchStrings)) {
        return false
    }
    for (searchString in searchStrings) {
        if (StringUtils.endsWith(sequence, searchString)) {
            return true
        }
    }
    return false
}

/**
 * Appends the suffix to the end of the string if the string does not
 * already end with the suffix.
 *
 * @param str The string.
 * @param suffix The suffix to append to the end of the string.
 * @param ignoreCase Indicates whether the compare should ignore case.
 * @param suffixes Additional suffixes that are valid terminators (optional).
 *
 * @return A new String if suffix was appended, the same string otherwise.
 */
private fun appendIfMissing(str: String?, suffix: CharSequence, ignoreCase: Boolean, vararg suffixes: CharSequence): String? {
    if (str == null || StringUtils.isEmpty(suffix) || StringUtils.endsWith(
            str,
            suffix,
            ignoreCase
        )
    ) {
        return str
    }
    if (suffixes != null && suffixes.size > 0) {
        for (s in suffixes) {
            if (StringUtils.endsWith(str, s, ignoreCase)) {
                return str
            }
        }
    }
    return str + suffix.toString()
}

/**
 * Appends the suffix to the end of the string if the string does not
 * already end with any of the suffixes.
 *
 * <pre>
 * StringUtils.appendIfMissing(null, null) = null
 * StringUtils.appendIfMissing("abc", null) = "abc"
 * StringUtils.appendIfMissing("", "xyz") = "xyz"
 * StringUtils.appendIfMissing("abc", "xyz") = "abcxyz"
 * StringUtils.appendIfMissing("abcxyz", "xyz") = "abcxyz"
 * StringUtils.appendIfMissing("abcXYZ", "xyz") = "abcXYZxyz"
</pre> *
 *
 * With additional suffixes,
 * <pre>
 * StringUtils.appendIfMissing(null, null, null) = null
 * StringUtils.appendIfMissing("abc", null, null) = "abc"
 * StringUtils.appendIfMissing("", "xyz", null) = "xyz"
 * StringUtils.appendIfMissing("abc", "xyz", new CharSequence[]{null}) = "abcxyz"
 * StringUtils.appendIfMissing("abc", "xyz", "") = "abc"
 * StringUtils.appendIfMissing("abc", "xyz", "mno") = "abcxyz"
 * StringUtils.appendIfMissing("abcxyz", "xyz", "mno") = "abcxyz"
 * StringUtils.appendIfMissing("abcmno", "xyz", "mno") = "abcmno"
 * StringUtils.appendIfMissing("abcXYZ", "xyz", "mno") = "abcXYZxyz"
 * StringUtils.appendIfMissing("abcMNO", "xyz", "mno") = "abcMNOxyz"
</pre> *
 *
 * @param str The string.
 * @param suffix The suffix to append to the end of the string.
 * @param suffixes Additional suffixes that are valid terminators.
 *
 * @return A new String if suffix was appended, the same string otherwise.
 *
 * @since 3.2
 */
fun appendIfMissing(str: String?, suffix: CharSequence?, vararg suffixes: CharSequence?): String {
    return StringUtils.appendIfMissing(str, suffix, false, *suffixes)
}

/**
 * Appends the suffix to the end of the string if the string does not
 * already end, case insensitive, with any of the suffixes.
 *
 * <pre>
 * StringUtils.appendIfMissingIgnoreCase(null, null) = null
 * StringUtils.appendIfMissingIgnoreCase("abc", null) = "abc"
 * StringUtils.appendIfMissingIgnoreCase("", "xyz") = "xyz"
 * StringUtils.appendIfMissingIgnoreCase("abc", "xyz") = "abcxyz"
 * StringUtils.appendIfMissingIgnoreCase("abcxyz", "xyz") = "abcxyz"
 * StringUtils.appendIfMissingIgnoreCase("abcXYZ", "xyz") = "abcXYZ"
</pre> *
 *
 * With additional suffixes,
 * <pre>
 * StringUtils.appendIfMissingIgnoreCase(null, null, null) = null
 * StringUtils.appendIfMissingIgnoreCase("abc", null, null) = "abc"
 * StringUtils.appendIfMissingIgnoreCase("", "xyz", null) = "xyz"
 * StringUtils.appendIfMissingIgnoreCase("abc", "xyz", new CharSequence[]{null}) = "abcxyz"
 * StringUtils.appendIfMissingIgnoreCase("abc", "xyz", "") = "abc"
 * StringUtils.appendIfMissingIgnoreCase("abc", "xyz", "mno") = "axyz"
 * StringUtils.appendIfMissingIgnoreCase("abcxyz", "xyz", "mno") = "abcxyz"
 * StringUtils.appendIfMissingIgnoreCase("abcmno", "xyz", "mno") = "abcmno"
 * StringUtils.appendIfMissingIgnoreCase("abcXYZ", "xyz", "mno") = "abcXYZ"
 * StringUtils.appendIfMissingIgnoreCase("abcMNO", "xyz", "mno") = "abcMNO"
</pre> *
 *
 * @param str The string.
 * @param suffix The suffix to append to the end of the string.
 * @param suffixes Additional suffixes that are valid terminators.
 *
 * @return A new String if suffix was appended, the same string otherwise.
 *
 * @since 3.2
 */
fun appendIfMissingIgnoreCase(str: String?, suffix: CharSequence?, vararg suffixes: CharSequence?): String {
    return StringUtils.appendIfMissing(str, suffix, true, *suffixes)
}

/**
 * Prepends the prefix to the start of the string if the string does not
 * already start with any of the prefixes.
 *
 * @param str The string.
 * @param prefix The prefix to prepend to the start of the string.
 * @param ignoreCase Indicates whether the compare should ignore case.
 * @param prefixes Additional prefixes that are valid (optional).
 *
 * @return A new String if prefix was prepended, the same string otherwise.
 */
private fun prependIfMissing(
    str: String?,
    prefix: CharSequence,
    ignoreCase: Boolean,
    vararg prefixes: CharSequence
): String? {
    if (str == null || StringUtils.isEmpty(prefix) || StringUtils.startsWith(
            str,
            prefix,
            ignoreCase
        )
    ) {
        return str
    }
    if (prefixes != null && prefixes.size > 0) {
        for (p in prefixes) {
            if (StringUtils.startsWith(str, p, ignoreCase)) {
                return str
            }
        }
    }
    return prefix.toString() + str
}

/**
 * Prepends the prefix to the start of the string if the string does not
 * already start with any of the prefixes.
 *
 * <pre>
 * StringUtils.prependIfMissing(null, null) = null
 * StringUtils.prependIfMissing("abc", null) = "abc"
 * StringUtils.prependIfMissing("", "xyz") = "xyz"
 * StringUtils.prependIfMissing("abc", "xyz") = "xyzabc"
 * StringUtils.prependIfMissing("xyzabc", "xyz") = "xyzabc"
 * StringUtils.prependIfMissing("XYZabc", "xyz") = "xyzXYZabc"
</pre> *
 *
 * With additional prefixes,
 * <pre>
 * StringUtils.prependIfMissing(null, null, null) = null
 * StringUtils.prependIfMissing("abc", null, null) = "abc"
 * StringUtils.prependIfMissing("", "xyz", null) = "xyz"
 * StringUtils.prependIfMissing("abc", "xyz", new CharSequence[]{null}) = "xyzabc"
 * StringUtils.prependIfMissing("abc", "xyz", "") = "abc"
 * StringUtils.prependIfMissing("abc", "xyz", "mno") = "xyzabc"
 * StringUtils.prependIfMissing("xyzabc", "xyz", "mno") = "xyzabc"
 * StringUtils.prependIfMissing("mnoabc", "xyz", "mno") = "mnoabc"
 * StringUtils.prependIfMissing("XYZabc", "xyz", "mno") = "xyzXYZabc"
 * StringUtils.prependIfMissing("MNOabc", "xyz", "mno") = "xyzMNOabc"
</pre> *
 *
 * @param str The string.
 * @param prefix The prefix to prepend to the start of the string.
 * @param prefixes Additional prefixes that are valid.
 *
 * @return A new String if prefix was prepended, the same string otherwise.
 *
 * @since 3.2
 */
fun prependIfMissing(str: String?, prefix: CharSequence?, vararg prefixes: CharSequence?): String {
    return StringUtils.prependIfMissing(str, prefix, false, *prefixes)
}

/**
 * Prepends the prefix to the start of the string if the string does not
 * already start, case insensitive, with any of the prefixes.
 *
 * <pre>
 * StringUtils.prependIfMissingIgnoreCase(null, null) = null
 * StringUtils.prependIfMissingIgnoreCase("abc", null) = "abc"
 * StringUtils.prependIfMissingIgnoreCase("", "xyz") = "xyz"
 * StringUtils.prependIfMissingIgnoreCase("abc", "xyz") = "xyzabc"
 * StringUtils.prependIfMissingIgnoreCase("xyzabc", "xyz") = "xyzabc"
 * StringUtils.prependIfMissingIgnoreCase("XYZabc", "xyz") = "XYZabc"
</pre> *
 *
 * With additional prefixes,
 * <pre>
 * StringUtils.prependIfMissingIgnoreCase(null, null, null) = null
 * StringUtils.prependIfMissingIgnoreCase("abc", null, null) = "abc"
 * StringUtils.prependIfMissingIgnoreCase("", "xyz", null) = "xyz"
 * StringUtils.prependIfMissingIgnoreCase("abc", "xyz", new CharSequence[]{null}) = "xyzabc"
 * StringUtils.prependIfMissingIgnoreCase("abc", "xyz", "") = "abc"
 * StringUtils.prependIfMissingIgnoreCase("abc", "xyz", "mno") = "xyzabc"
 * StringUtils.prependIfMissingIgnoreCase("xyzabc", "xyz", "mno") = "xyzabc"
 * StringUtils.prependIfMissingIgnoreCase("mnoabc", "xyz", "mno") = "mnoabc"
 * StringUtils.prependIfMissingIgnoreCase("XYZabc", "xyz", "mno") = "XYZabc"
 * StringUtils.prependIfMissingIgnoreCase("MNOabc", "xyz", "mno") = "MNOabc"
</pre> *
 *
 * @param str The string.
 * @param prefix The prefix to prepend to the start of the string.
 * @param prefixes Additional prefixes that are valid (optional).
 *
 * @return A new String if prefix was prepended, the same string otherwise.
 *
 * @since 3.2
 */
fun prependIfMissingIgnoreCase(str: String?, prefix: CharSequence?, vararg prefixes: CharSequence?): String {
    return StringUtils.prependIfMissing(str, prefix, true, *prefixes)
}

/**
 * Converts a `byte[]` to a String using the specified character encoding.
 *
 * @param bytes
 * the byte array to read from
 * @param charsetName
 * the encoding to use, if null then use the platform default
 * @return a new String
 * @throws UnsupportedEncodingException
 * If the named charset is not supported
 * @throws NullPointerException
 * if the input is null
 * @since 3.1
 */
@Deprecated("use {@link StringUtils#toEncodedString(byte[], Charset)} instead of String constants in your code\n" + "      ")
@Throws(
    UnsupportedEncodingException::class
)
fun toString(bytes: ByteArray?, charsetName: String?): String {
    return if (charsetName != null) String(bytes!!, charsetName) else String(bytes!!, Charset.defaultCharset())
}

/**
 * Converts a `byte[]` to a String using the specified character encoding.
 *
 * @param bytes
 * the byte array to read from
 * @param charset
 * the encoding to use, if null then use the platform default
 * @return a new String
 * @throws NullPointerException
 * if `bytes` is null
 * @since 3.2
 * @since 3.3 No longer throws [UnsupportedEncodingException].
 */
fun toEncodedString(bytes: ByteArray?, charset: Charset?): String {
    return String(bytes!!, charset ?: Charset.defaultCharset())
}

/**
 *
 *
 * Wraps a string with a char.
 *
 *
 * <pre>
 * StringUtils.wrap(null, *)        = null
 * StringUtils.wrap("", *)          = ""
 * StringUtils.wrap("ab", '\0')     = "ab"
 * StringUtils.wrap("ab", 'x')      = "xabx"
 * StringUtils.wrap("ab", '\'')     = "'ab'"
 * StringUtils.wrap("\"ab\"", '\"') = "\"\"ab\"\""
</pre> *
 *
 * @param str
 * the string to be wrapped, may be `null`
 * @param wrapWith
 * the char that will wrap `str`
 * @return the wrapped string, or `null` if `str==null`
 * @since 3.4
 */
fun wrap(str: String, wrapWith: Char): String {
    return if (StringUtils.isEmpty(str) || wrapWith == CharUtils.NUL) {
        str
    } else wrapWith.toString() + str + wrapWith
}

/**
 *
 *
 * Wraps a String with another String.
 *
 *
 *
 *
 * A `null` input String returns `null`.
 *
 *
 * <pre>
 * StringUtils.wrap(null, *)         = null
 * StringUtils.wrap("", *)           = ""
 * StringUtils.wrap("ab", null)      = "ab"
 * StringUtils.wrap("ab", "x")       = "xabx"
 * StringUtils.wrap("ab", "\"")      = "\"ab\""
 * StringUtils.wrap("\"ab\"", "\"")  = "\"\"ab\"\""
 * StringUtils.wrap("ab", "'")       = "'ab'"
 * StringUtils.wrap("'abcd'", "'")   = "''abcd''"
 * StringUtils.wrap("\"abcd\"", "'") = "'\"abcd\"'"
 * StringUtils.wrap("'abcd'", "\"")  = "\"'abcd'\""
</pre> *
 *
 * @param str
 * the String to be wrapper, may be null
 * @param wrapWith
 * the String that will wrap str
 * @return wrapped String, `null` if null String input
 * @since 3.4
 */
fun wrap(str: String, wrapWith: String): String {
    return if (StringUtils.isEmpty(str) || StringUtils.isEmpty(wrapWith)) {
        str
    } else wrapWith + str + wrapWith
}

/**
 *
 *
 * Wraps a string with a char if that char is missing from the start or end of the given string.
 *
 *
 * <pre>
 * StringUtils.wrapIfMissing(null, *)        = null
 * StringUtils.wrapIfMissing("", *)          = ""
 * StringUtils.wrapIfMissing("ab", '\0')     = "ab"
 * StringUtils.wrapIfMissing("ab", 'x')      = "xabx"
 * StringUtils.wrapIfMissing("ab", '\'')     = "'ab'"
 * StringUtils.wrapIfMissing("\"ab\"", '\"') = "\"ab\""
 * StringUtils.wrapIfMissing("/", '/')  = "/"
 * StringUtils.wrapIfMissing("a/b/c", '/')  = "/a/b/c/"
 * StringUtils.wrapIfMissing("/a/b/c", '/')  = "/a/b/c/"
 * StringUtils.wrapIfMissing("a/b/c/", '/')  = "/a/b/c/"
</pre> *
 *
 * @param str
 * the string to be wrapped, may be `null`
 * @param wrapWith
 * the char that will wrap `str`
 * @return the wrapped string, or `null` if `str==null`
 * @since 3.5
 */
fun wrapIfMissing(str: String, wrapWith: Char): String {
    if (StringUtils.isEmpty(str) || wrapWith == CharUtils.NUL) {
        return str
    }
    val builder = StringBuilder(str.length + 2)
    if (str[0] != wrapWith) {
        builder.append(wrapWith)
    }
    builder.append(str)
    if (str[str.length - 1] != wrapWith) {
        builder.append(wrapWith)
    }
    return builder.toString()
}

/**
 *
 *
 * Wraps a string with a string if that string is missing from the start or end of the given string.
 *
 *
 * <pre>
 * StringUtils.wrapIfMissing(null, *)         = null
 * StringUtils.wrapIfMissing("", *)           = ""
 * StringUtils.wrapIfMissing("ab", null)      = "ab"
 * StringUtils.wrapIfMissing("ab", "x")       = "xabx"
 * StringUtils.wrapIfMissing("ab", "\"")      = "\"ab\""
 * StringUtils.wrapIfMissing("\"ab\"", "\"")  = "\"ab\""
 * StringUtils.wrapIfMissing("ab", "'")       = "'ab'"
 * StringUtils.wrapIfMissing("'abcd'", "'")   = "'abcd'"
 * StringUtils.wrapIfMissing("\"abcd\"", "'") = "'\"abcd\"'"
 * StringUtils.wrapIfMissing("'abcd'", "\"")  = "\"'abcd'\""
 * StringUtils.wrapIfMissing("/", "/")  = "/"
 * StringUtils.wrapIfMissing("a/b/c", "/")  = "/a/b/c/"
 * StringUtils.wrapIfMissing("/a/b/c", "/")  = "/a/b/c/"
 * StringUtils.wrapIfMissing("a/b/c/", "/")  = "/a/b/c/"
</pre> *
 *
 * @param str
 * the string to be wrapped, may be `null`
 * @param wrapWith
 * the char that will wrap `str`
 * @return the wrapped string, or `null` if `str==null`
 * @since 3.5
 */
fun wrapIfMissing(str: String, wrapWith: String): String {
    if (StringUtils.isEmpty(str) || StringUtils.isEmpty(wrapWith)) {
        return str
    }
    val builder = StringBuilder(str.length + wrapWith.length + wrapWith.length)
    if (!str.startsWith(wrapWith)) {
        builder.append(wrapWith)
    }
    builder.append(str)
    if (!str.endsWith(wrapWith)) {
        builder.append(wrapWith)
    }
    return builder.toString()
}

/**
 *
 *
 * Unwraps a given string from anther string.
 *
 *
 * <pre>
 * StringUtils.unwrap(null, null)         = null
 * StringUtils.unwrap(null, "")           = null
 * StringUtils.unwrap(null, "1")          = null
 * StringUtils.unwrap("\'abc\'", "\'")    = "abc"
 * StringUtils.unwrap("\"abc\"", "\"")    = "abc"
 * StringUtils.unwrap("AABabcBAA", "AA")  = "BabcB"
 * StringUtils.unwrap("A", "#")           = "A"
 * StringUtils.unwrap("#A", "#")          = "#A"
 * StringUtils.unwrap("A#", "#")          = "A#"
</pre> *
 *
 * @param str
 * the String to be unwrapped, can be null
 * @param wrapToken
 * the String used to unwrap
 * @return unwrapped String or the original string
 * if it is not quoted properly with the wrapToken
 * @since 3.6
 */
fun unwrap(str: String, wrapToken: String): String {
    if (StringUtils.isEmpty(str) || StringUtils.isEmpty(wrapToken)) {
        return str
    }
    if (StringUtils.startsWith(str, wrapToken) && StringUtils.endsWith(str, wrapToken)) {
        val startIndex = str.indexOf(wrapToken)
        val endIndex = str.lastIndexOf(wrapToken)
        val wrapLength = wrapToken.length
        if (startIndex != -1 && endIndex != -1) {
            return str.substring(startIndex + wrapLength, endIndex)
        }
    }
    return str
}

/**
 *
 *
 * Unwraps a given string from a character.
 *
 *
 * <pre>
 * StringUtils.unwrap(null, null)         = null
 * StringUtils.unwrap(null, '\0')         = null
 * StringUtils.unwrap(null, '1')          = null
 * StringUtils.unwrap("\'abc\'", '\'')    = "abc"
 * StringUtils.unwrap("AABabcBAA", 'A')  = "ABabcBA"
 * StringUtils.unwrap("A", '#')           = "A"
 * StringUtils.unwrap("#A", '#')          = "#A"
 * StringUtils.unwrap("A#", '#')          = "A#"
</pre> *
 *
 * @param str
 * the String to be unwrapped, can be null
 * @param wrapChar
 * the character used to unwrap
 * @return unwrapped String or the original string
 * if it is not quoted properly with the wrapChar
 * @since 3.6
 */
fun unwrap(str: String, wrapChar: Char): String {
    if (StringUtils.isEmpty(str) || wrapChar == CharUtils.NUL) {
        return str
    }
    if (str[0] == wrapChar && str[str.length - 1] == wrapChar) {
        val startIndex = 0
        val endIndex = str.length - 1
        if (endIndex != -1) {
            return str.substring(startIndex + 1, endIndex)
        }
    }
    return str
}

/**
 *
 * Converts a `CharSequence` into an array of code points.
 *
 *
 * Valid pairs of surrogate code units will be converted into a single supplementary
 * code point. Isolated surrogate code units (i.e. a high surrogate not followed by a low surrogate or
 * a low surrogate not preceded by a high surrogate) will be returned as-is.
 *
 * <pre>
 * StringUtils.toCodePoints(null)   =  null
 * StringUtils.toCodePoints("")     =  []  // empty array
</pre> *
 *
 * @param str the character sequence to convert
 * @return an array of code points
 * @since 3.6
 */
fun toCodePoints(str: CharSequence?): IntArray? {
    if (str == null) {
        return null
    }
    if (str.length == 0) {
        return ArrayUtils.EMPTY_INT_ARRAY
    }
    val s = str.toString()
    val result = IntArray(s.codePointCount(0, s.length))
    var index = 0
    for (i in result.indices) {
        result[i] = s.codePointAt(index)
        index += Character.charCount(result[i])
    }
    return result
}

/**
 * Returns the string representation of the `char` array or null.
 *
 * @param value the character array.
 * @return a String or null
 * @see String.valueOf
 * @since 3.9
 */
fun valueOf(value: CharArray?): String? {
    return value?.let { String(it) }
}
}