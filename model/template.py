# coding=utf8
# the above tag defines encoding for this document and is for Python 2.x compatibility

import re

regex = r"--regex--"

test_str = ('''--text--''')

matches = re.finditer(regex, test_str, re.MULTILINE)
data = ""
for matchNum, match in enumerate(matches):
    matchNum = matchNum + 1

    for groupNum in range(0, len(match.groups())):
        groupNum = groupNum + 1

        # print ("Group {groupNum} found at {start}-{end}: {group}".format(groupNum=groupNum, start=match.start(groupNum),
        #                                                                  end=match.end(groupNum),
        #                                                                  group=match.group(groupNum)))
        data += match.group(groupNum) + ',,,'

print(data)
# Note: for Python 2.7 compatibility, use ur"" to prefix the regex and u"" to prefix the test string and substitution.

