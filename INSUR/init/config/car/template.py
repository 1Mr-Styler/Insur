# coding=utf8
# the above tag defines encoding for this document and is for Python 2.x compatibility

import re

regex = (
    r'''--regex--''')

test_str = ('''--text--''')

matches = re.finditer(regex, test_str, re.MULTILINE)
data = ""
for matchNum, match in enumerate(matches):
    matchNum = matchNum + 1
    print("{0},,,{1},,,{2},{3},{4},,,{5},,,{6},,,{7},,,{8},,,{9},,,{10},,,{11},,,{12},,,{13}".format(
        match.group('name'),
        match.group('policy'),
        match.group('addr1'),
        match.group('addr2'),
        match.group('addr3'),
        match.group('nric'),
        match.group('occupation'),
        match.group('dfrom'),
        match.group('dto'),
        match.group('mnfd'),
        match.group('make'),
        match.group('seat'),
        match.group('coverage'),
        match.group('premium'),
    ))


# Note: for Python 2.7 compatibility, use ur"" to prefix the regex and u"" to prefix the test string and substitution.
