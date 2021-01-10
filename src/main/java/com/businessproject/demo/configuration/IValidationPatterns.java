package com.businessproject.demo.configuration;

public interface IValidationPatterns {
    /**
     * Pattern for any name start with a capital letter and continues with small letters
     */
    String NAME_PATTERN = "^[A-Z][a-z]+$";
    /**
     * Pattern for any username that must not start with _ or . and can continue with as
     * many letters and numbers that are not separated by whitespaces, but can be separated
     * by _ and/or . in between. Must not end with _ or .
     * Example: "someone34_example" or "ex_a34mp.le"
     */
    String USERNAME_PATTERN = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
    /**
     * Pattern for any phone number
     */
    String PHONE_PATTERN = "^\\d{10}$" + // Regex for 10 digit numbers
            // Regular expression to allow numbers with whitespaces, dots or hyphens
            // Like 202.555.0125 or 202-555-0125:
            "|^(\\d{3}[- .]?){2}\\d{4}$" +
            // Regular expression to allow number with parentheses like (202)5550125:
            "|^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" +
            // Regular expressions to allow numbers Number with international prefix like +111 (202) 555-0125:
            "|^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" +
            // Regular expression to allow numbers like +111 123 456 789:
            "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" +
            // Pattern to allow numbers like +111 123 45 67 89:
            "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
}
