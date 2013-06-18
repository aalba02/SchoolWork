#! /usr/bin/python
import sys
import mechanize
import lxml
import re
from lxml.html import parse, tostring


URL = "http://stackoverflow.com"
results = []
filename = ""

# formats the arguments according to searching in Stack Overflow
# arg1 = tag, arg2 = title, arg3 = body


# two arguments = [tag] search_keyword
def setTagTitle(arg):
	global filename
	keywords = "[" + arg[1] + "] " + arg[2]
	filename += arg[1] + "_" + arg[2]
	search(keywords)

# three arguments = [tag] title:title_keyword body:"content"
def setTagTitleBody(arg):
	global filename
	title = "title:" + arg[2]
	body = " body:\"" + arg[3] + "\""
	keywords = "[" + arg[1] + "] " + title + body
	filename += arg[1] + "_" + arg[2] + "_" + arg[3]
	search(keywords)

def search(keyword):
	print "Search: " + keyword
	b = mechanize.Browser()
	b.set_handle_robots(False)
	b.open(URL)
	b.select_form(nr = 0)
	b['q'] = keyword
	fd = b.submit()
	html = fd.read()
	
	pattern = re.compile('<a href="(.*)" title=.*>50</a>')
	findPattern = re.findall(pattern, html)

	newURL = URL + findPattern[0].replace("&amp;", "&");
	getData(newURL)

def getData(url):
	doc = parse(url).getroot()
	i = 1
	for link in doc.cssselect('div.summary a'):
		href = link.get('href')
		if 'tagged' not in href and 'users' not in href:
			post = URL + link.get('href')
			results.append({'url': post, 'title':link.get('title')})
			i+=1
	printResults()
		
def printResults():
	print len(results)
	try:
		f = open("results/" + filename + ".html", 'a')
		style = '<head><link rel="stylesheet" type="text/css" href="result.css" /></head>'
		f.write('<html>' + style + '<body>')
		f.write('<h1> Results for Search: ' + filename + "</h1>")
		f.write('<div id="results">')
		f.write('  <table>')
		for elem in results:
			f.write('  <tr>')
			f.write('<td>Title: ' + elem['title'] + '</td>')
			f.write('<td> <a href="' + elem['url'] + '">goToResult</a></td>')
		f.write('  </tr>')
		f.write('  </table>')
		f.write('</div>')
		f.write('</body></html>')
		f.close()
	except UnicodeEncodeError:
		print "Couldn't transfer"
# Gets arguments from the user
def main():
	if(len(sys.argv) <= 2):
		print "You did not enter enough arguments"
	elif(len(sys.argv) == 3):
		setTagTitle(sys.argv)
	elif len(sys.argv) == 4:
		setTagTitleBody(sys.argv)
	else:
		print "You have entered too many arguments"
	
if __name__ == '__main__':main()
	
