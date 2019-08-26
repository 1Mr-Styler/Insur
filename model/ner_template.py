# coding=utf8
# the above tag defines encoding for this document and is for Python 2.x compatibility

import re

regex = r"<([A-Z_]+)>([0-9a-zA-Z-\s,\.\/]+)</[A-Z_]+>"

test_str = ('''--text--''')

matches = re.finditer(regex, test_str, re.MULTILINE)
data = ""
for matchNum, match in enumerate(matches):
    matchNum = matchNum + 1

    for groupNum in range(0, len(match.groups())):
        groupNum = groupNum + 1

        if groupNum == 1:
            data += match.group(groupNum)
        elif groupNum == 2:
            data += '----' + match.group(groupNum) + ',,,'

print(data)
# Note: for Python 2.7 compatibility, use ur"" to prefix the regex and u"" to prefix the test string and substitution.
