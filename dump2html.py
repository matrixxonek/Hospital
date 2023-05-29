#! /bin/python
import sys
import shlex
import re

if len(sys.argv) == 1:
	print('dump2html.py wersja 1.1.0')
	print('Składnia: python str2html.py ŹRÓDŁO...')
	print('Generowanie pliku HTML ze strukturą bazy danych z pliku zrzutu.')
	print('Pliki ŹRÓDŁO muszą być plikami zrzutu bazych danych MySQL. W przeciwnym wypadku są one ignorowane.')
	exit()

for f in range(1, len(sys.argv)):
	sql = open(sys.argv[f], 'r').readlines()
	name = ''
	data = []
	l = 0
	i = 0
	while i < len(sql):
		if sql[i].startswith('-- ') or sql[i].startswith('\n'):
			i = i+1
			continue
		sql[i] = sql[i].replace('\n', '')
		if sql[i].startswith('CREATE TABLE IF NOT EXISTS'):
			l = len(data)
			data.append({'name': sql[i].split(' ')[5].split('.', 1)[1][1:-1], 'cols': [], 'constr': [], 'desc': ''})
			while not sql[i].endswith(';\n'):
				i = i+1
				if sql[i].startswith('  `'):
					data[l]['cols'].append({ 'name': shlex.split(sql[i])[0][1:-1], 'type': shlex.split(sql[i])[1], 'desc': ''})
					if "COMMENT '" in sql[i]:
						data[l]['cols'][-1]['desc'] = shlex.split(sql[i])[shlex.split(sql[i]).index('COMMENT')+1][:-1]
				elif sql[i].startswith('COMMENT'):
					data[l]['desc'] = sql[i].split(' ', 2)[2].replace('\'', '')[:-2]
				elif sql[i].startswith('  UNIQUE INDEX'):
					data[l]['constr'].append({ 'name': re.search(r'`.*?`', sql[i]).group()[1:-1], 'desc': '; '.join(i[1:-1] for i in re.findall(r'`.*?`', sql[i])[1:]), 'type': 'UNIQUE' })
				elif sql[i].startswith('  PRIMARY KEY'):
					data[l]['constr'].append({ 'name': 'PRIMARY', 'desc':'; '.join(i[1:-1] for i in re.findall(r'`.*?`', sql[i])), 'type': 'PRIMARY KEY' })
				elif sql[i+1].startswith('    FOREIGN KEY'):
					data[l]['constr'].append({ 'name': re.search(r'`.*?`', sql[i]).group()[1:-1], 'desc': '; '.join(i[1:-1] for i in re.findall(r'`.*?`', sql[i+1])) + '-><a href="#' + re.findall(r'`.*?`', sql[i+2])[1][1:-1] + '">' + re.findall(r'`.*?`', sql[i+2])[1][1:-1] + '</a>', 'type': 'FOREIGN KEY' })
					i = i+2
		elif sql[i].startswith('CREATE SCHEMA IF NOT EXISTS'):
			name = shlex.split(sql[i].replace('`', '\''))[5]
		i = i+1

	data = sorted(data, key=lambda d: d['name'])
	for i in range(len(data)):
		data[i]['constr'] = sorted(data[i]['constr'], key=lambda d: d['name'])
	fhtml = open('dokumentacja_bazy_danych_' + name + '.htm', 'w')
	fhtml.write('<html><head><style>table{border-collapse:collapse;width: 100%;}tr,td{border: 1px solid #000;}a{text-decoration:none;}@media print{a:link,a:visited{color:#000;}a{pointer-events: none;}.totop{display:none;}}p{font-size:10pt;}td{vertical-align:top;}body{font-family:"Tahoma","Arial","Helvetica",sans-serif;}h1,h2{font-size:14pt;font-weight:bold;margin-top:12pt;margin-bottom:12pt;background-color:#fff;}.subheading{font-size:10pt;margin-top:12pt;margin-bottom:12pt;}th{font-size:8pt;border-top:1px solid #000;border-left:1px solid #000;border-right: 1px solid #000;border-bottom: 1px solid #000;color: #fff;font-weight:bold;background-color:#000;}td{font-size:8pt;}.totop{font-size:10px;}</style></head><body>')
	fhtml.write('<h1>Opis struktury zbiorów danych</h1>')
	fhtml.write('<p>Poniżej przedstawiono opis struktury zbiorów bazy danych wskazujących zawartość poszczególnych pól informacyjnych i powiązania między nimi</p>')
	fhtml.write('<hr />')
	fhtml.write('<p id="tables">Tabele</p>')
	fhtml.write('<table id="spis_tresci" cellpadding="2">')
	fhtml.write('<tr><th width="25" align="left">L.p.</th><th width="100" align="left">Nazwa</th><th align="left" class="nagl_spec">Opis</th></tr>')
	for i in range(len(data)):
		fhtml.write('<tr><td>' + str(i+1) + '.</td><td><a href="#' + data[i]['name'] + '">' + data[i]['name'] + '</a></td><td>' + data[i]['desc'] + '</td></tr>')
	fhtml.write('</table><br /><hr />')
	for i in range(len(data)):
		fhtml.write('<h2 id="' + data[i]['name'] + '">' + str(i+1) + '. Tabela ' + data[i]['name'] + '<a href="#tables" class="totop">(wróć na górę)</a></h2>')
		fhtml.write('<p>' + data[i]['desc'] + '</p>')
		fhtml.write('<table id="spis_tresci" cellpadding="2">')
		fhtml.write('<tr><th width="25" align="left">L.p.</th><th width="150" align="left">Nazwa</th><th align="left" class="nagl_spec">Opis</th><th width="100">Typ</th></tr>')
		for j in range(len(data[i]['cols'])):
			fhtml.write('<tr><td>' + str(j+1) + '.</td><td>' + data[i]['cols'][j]['name'] + '</td><td>' + data[i]['cols'][j]['desc'] + '</td><td>' + data[i]['cols'][j]['type'] + '</td></tr>')
		fhtml.write('<tr><td colspan="4">Założenia:</td></tr>')
		for j in range(len(data[i]['constr'])):
			fhtml.write('<tr><td>' + str(j+1) + '.</td><td>' + data[i]['constr'][j]['name'] + '</td><td>' + data[i]['constr'][j]['desc'] + '</td><td>' + data[i]['constr'][j]['type'] + '</td></tr>')
		fhtml.write('</table><br />')
	fhtml.write('</body></html>')
	fhtml.close()