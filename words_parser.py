
import urllib3
import json

http = urllib3.PoolManager()


import pandas as pd
excel_path = '3000.xlsx'
d = pd.read_excel(excel_path, sheetname=None)
print(d['3000'].Word)
wordslist=d['3000'].Word
wordslist=d['3000'].Word.tolist()
wordslist = wordslist[2000:2999]


results_list = dict()
error_list =[] # 词典里没有的words
for eachword in wordslist:
    try:
        words = http.request('GET', 'https://www.dictionaryapi.com/api/v3/references/ithesaurus/json/'+eachword+'?key=65744f8d-92b4-4b39-ba46-b68707c27190')
        words_dict = json.loads(words.data.decode('UTF-8'))
        words_dict = words_dict[0]
        body = words_dict['def'][0]['sseq']
        current_word = []  # current_word is a list of different meanings for this word
        for everylist in body:
            for each in everylist:
                sep = each[1]
                near = sep['near_list']
                nearlist = []   
                for eachlist in near:
                    for each in eachlist:
                        nearlist.append(each['wd'])   # *
                rel = sep['rel_list']
                rellist = []    
                for eachlist in rel:
                    for each in eachlist:
                        rellist.append(each['wd'])    # *
                syn = sep['syn_list']
                synlist = []
                for eachlist in syn:
                    for each in eachlist:
                        synlist.append(each['wd'])     # *
                dt = sep['dt']
                explain = dt[0][1]
                example = dt[1][1][0]['t'].replace('{it}','').replace('{/it}','') # *
                temp_dict = dict()
                temp_dict['example'] = example
                temp_dict['explain'] = explain
                temp_dict['near'] = nearlist
                temp_dict['rel'] = rellist
                temp_dict['syn'] = synlist
                current_word.append(temp_dict)
    except TypeError:
        error_list.append(eachword)
    except BaseException as err:
        print(err)
        
    else:    
        results_list[eachword] = current_word

    success_list=list(results_list.keys())
    combined = list(set(success_list).union(set(error_list)))
    goals = list(set(wordslist).difference(set(combined)))
    
    
with open('gre_words3.json', 'w') as outfile:  
    json.dump(results_list, outfile,indent=4)




    
        
            
    
        





