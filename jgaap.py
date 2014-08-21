#!/usr/bin/python

import sys
import re

'''
jgaap experiment result file processor
usage:
> python jgaap.py <fileToProcess> <op>

@param op:
    single
    multi_ar
    multi_fold
'''

FILE_KEYWORD     = 'processed'
AUTHOR_KEYWORD   = 'author-'
print_short = False

def single(filename):
  readfile = open(filename, 'r')
  totalScore = 0
  count = 0.0
  results = [0] * 40
  other = 0
  average = 0
  while True:
    line = readfile.readline()
    if (len(line) == 0):
      break
    if (line[:9] == FILE_KEYWORD):
      target = AUTHOR_KEYWORD + line[10:13]
      line = readfile.readline()
      while not target in line:
        line = readfile.readline()
      dotindex = line.index('.')
      number = int(line[0:dotindex])
      #resultfile = line[:58]
      #print target + '\t' + number + '\t' + resultfile[:len(resultfile)-1]
      #sys.stdout.write(number + ",")
      totalScore += int(number)
      count += 1.0
      average += (2.5 - 0.0625 * (int(number)-1))
      for i in range(number-1, 40):
        results[i] += 1

  if results[-1] != count:
    print 'Something is wrong, counts is not equal'
    sys.exit(1)

  output = {}
  output['results'] = results
  output['other'] = other
  output['count'] = count
  output['totalScore'] = totalScore
  output['average'] = average
  output['filename'] = filename
  return output
    
def multi_ar(filename, language_number=0):
  '''Go over AR=[1,5] and print the results all together
  '''
  results = []
  for ar in range(1, 6):
    this_filename = filename + str(ar)
    if language_number != 0:
      this_filename = this_filename + '_lang' + str(language_number)
    this_filename = this_filename + '.txt'
    results.append(single(this_filename))
  print_results_excel(results)
  return

def multi_fold(filename, min_val, max_val):
  '''Go over FOLD[min, max] and print the results all together
  '''
  index_last_slash = filename.rfind('/')
  if index_last_slash == -1:
    index_last_slash = 0
  last_part = filename[index_last_slash:]
  first_digit = re.search("\d", last_part)
  if first_digit:
    index = first_digit.start()
  else:
    print "Something is wrong in jgaap_processor, multi_fold"
    sys.exit(1)
  for folds in range(min_val, max_val+1):
    this_filename = filename[:index_last_slash]
    this_filename += last_part[0:index] + str(folds) + last_part[index+1:]
    multi_ar(this_filename)
  return

def translate(filename, language_number):
  multi_ar(filename, language_number)
  return

def print_results_excel(outputs):
  percentage_ratio = 100 / outputs[0]['count']
  print '-' * 81
  print outputs[0]['filename']
  for top in range(0, 4):
    tops = ['', '', '', '', '']
    for index in range(0, len(outputs)):
      tops[index] = tops[index] + str(outputs[index]['results'][top] * percentage_ratio)
    print ','.join(map(str, tops))
  return
            

def print_results(output, filename):
    percentage_ratio = 100 / output['count']
    print '-' * 81
    print filename
    if print_short:
        #print 'Top-1: %.2f' %  (output['results'][0] * percentage_ratio) + \
        #'\tTop-2: %.2f' % (output['results'][1] * percentage_ratio) + \
        #'\tTop-3: %.2f' % (output['results'][2] * percentage_ratio) + \
        #'\tTop-4: %.2f' % (output['results'][3] * percentage_ratio)
        print 'Top-X\n' + \
        str(output['results'][0] * percentage_ratio) + '\n' + \
        str(output['results'][1] * percentage_ratio) + '\n' + \
        str(output['results'][2] * percentage_ratio) + '\n' + \
        str(output['results'][3] * percentage_ratio)
        return;
    print 'Total result: ' + str(output['count'])
    print 'Average top score: #' + str(output['totalScore']/output['count']) + ', %' + str(output['average'])
    print 'Top 1: #' + str(output['results'][0])     + ', %' + str(output['results'][0] * percentage_ratio)
    print 'Top 2: #' + str(output['results'][1])     + ', %' + str(output['results'][1] * percentage_ratio)
    print 'Top 3: #' + str(output['results'][2])   + ', %' + str(output['results'][2] * percentage_ratio)
    print 'Top 4: #' + str(output['results'][3])    + ', %' + str(output['results'][3] * percentage_ratio)
    print 'Other: #' + str(output['other'])   + ', %' + str(output['other'] * percentage_ratio)
    
arg_size = len(sys.argv)
filename = sys.argv[1]
op = sys.argv[2]
if (arg_size >= 4):
    print_short = True

if op == 'single':
  print_results(single(filename), filename)
elif op == 'multi_ar':
  multi_ar(filename)
elif op.startswith('multi_fold'):
  fold = int(op[-1])
  multi_fold(filename, 1, fold)
elif op == 'translate':
  translate(filename, int(sys.argv[4]))
