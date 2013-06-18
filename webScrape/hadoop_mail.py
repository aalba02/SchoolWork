from lxml.html import parse  
from lxml import etree     
import re
import sys
import urllib2

URL = 'http://hadoop.apache.org/mailing_lists.html'
results = []
bt = 1

def getArchiveUrls(num):
	num = int(num)
	n = 0
	doc = parse(URL).getroot()
	for link in doc.cssselect('div.section a'):
		if link.text_content() == 'Archives':
			n+=1
			if num == n:
				getThreadUrls(link.get('href'))	
				
def getThreadUrls(url):
	doc = parse(url).getroot()
	for link in doc.cssselect('span.links a'):
		if link.text_content() == 'Thread':
			getPostUrl(url + link.get('href'))

def getPostUrl(url):
	doc = parse(url).getroot()
	u = re.sub('thread', '', url)
	for link in doc.cssselect('td.subject a'):
		searchPost(u + link.get('href'))

def searchPost(url):
	try:
		global bt
		print "still going....", bt
		bt = bt + 1
		doc = parse(url).getroot()
		for i,j in map(None, doc.cssselect('pre'), doc.cssselect('tr.subject td.right')):
			try:
				if hasKeyword(i.text_content(), j.text_content()):
					results.append({'url': url, 'title':j.text_content()})
			except UnicodeDecodeError:
				print "Can't convert"
	except IOError:
		print "Broken Url"

def hasKeyword(pre, title):
	t = title.lower()
	if "sudo" in pre:
		return True
	if "<property>" in pre:
		return True
	if "<code>" in pre:
		return True
	if "<configuration>" in pre:
		return True
	if "<name>" in pre:
		return True
	if "{code}" in pre:
		return True
	return False

def printResults():
	try:
		f = open('hadoop-mapreduce-issues', 'a')
		f.write('<html><body>')
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
	
def main():
	print "{code}"
	if(int(sys.argv[1]) > 14 or int(sys.argv[1]) < 1):
		print "Argument should be between 1 & 14"
		sys.exit()
	whichOne = sys.argv[1]
	getArchiveUrls(whichOne)
	printResults()
		
if __name__ == '__main__':main()