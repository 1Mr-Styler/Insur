import datetime

dt = datetime.datetime(2005, 1, 1)
end = datetime.datetime(2010, 12, 30, 23, 59, 59)
step = datetime.timedelta(seconds=86399)

result = []

while dt < end:
    print dt.strftime('%d-%m-%Y'), "\tDATE"
    dt += step
