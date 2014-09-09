/*
 * Copyright 2013 Proofpoint Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.kairosdb.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterSet
{
  private static CharacterSet INTERNAL_CHARACTER_SET;
  private static final String DEFAULT_REGEX_PATTERN = "^[a-zA-Z0-9\\-\\./_]*$";
  private final Pattern regex;

  private CharacterSet(String regexPattern)
  {
    regex = Pattern.compile(regexPattern);
  }

  /**
   * Static wrapper for internal version of isValid
   */
  public static boolean isValid(String s)
  {
    return provideInternalCharacterSet().isValidInternal(s);
  }

  /**
   * Returns true if the specified string contains a valid set of characters
   * @param s string to test
   * @return true if all characters in the string are valid
   */
  private boolean isValidInternal(String s)
  {
    Matcher matcher = regex.matcher(s);
    return matcher.matches();
  }

  private static CharacterSet provideInternalCharacterSet() {
    if (INTERNAL_CHARACTER_SET == null) {
      INTERNAL_CHARACTER_SET = newCharacterSet();
    }
    return INTERNAL_CHARACTER_SET;
  }

  /**
   * Factory method for creating a CharacterSet object with a command line configurable pattern
   */
  private static CharacterSet newCharacterSet() {
    String regexPattern = System.getProperty("character.set.regex.pattern");
    if (regexPattern == null || regexPattern.isEmpty()) {
      return new CharacterSet(DEFAULT_REGEX_PATTERN);
    }
    return new CharacterSet(regexPattern);
  }
}