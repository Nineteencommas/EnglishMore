
from pymongo import MongoClient
from bson.json_util import dumps
import json

client = MongoClient('mongodb+srv://xinyi:ZXCVzxcv9859@forenglishmore-8tghp.mongodb.net/test?retryWrites=true')
db = client.EnglishMore

try:
    contacts = db.topicData.find()
    lala = dumps(contacts)
except Exception:
    lala = dumps({'error' : str(Exception)})
    
    
    


import json

with open('gre_words2.json') as json_file:  
    data = json.load(json_file)
    

keys = list(data.keys())



import pymongo

myclient = pymongo.MongoClient('mongodb+srv://xinyi:ZXCVzxcv9859@forenglishmore-8tghp.mongodb.net/test?retryWrites=true')
mydb = myclient.EnglishMore

mycol = mydb['wordsList']



i = 0
temp = {}
temp['deckerInfo'] = "TOEFLDecker1"
for i in range(0,len(data)):
    temp[keys[i]] = data[keys[i]]
    
    if ((i % 50 == 0) and i != 0):
        x = mycol.insert_one(temp)
        print(x)
        temp = {}
        temp['deckerInfo'] = "TOEFLDecker"+ str(( i // 50)+1)
    if (i == len(data) - 1 ):
        x = mycol.insert_one(temp)
        print(x)
        
    

test = '{"lala" : "lalala"}'
test = dumps(test)

    
@app.route("/get_word_list")
def get_word_list():
    decker = request.args.get('decker')
    try:
        word_list = db.wordsList.find({ "deckerInfo" : decker}) # 
        before_clean = dumps(word_list)
        after_clean = before_clean.pop("deckerInfo")

        return after_clean
    except Exception, e:
        return dumps({'error' : str(e)})
    
from flask import Flask
from flask import request
from pymongo import MongoClient
from bson.json_util import dumps
import json


client = MongoClient('mongodb+srv://xinyi:ZXCVzxcv9859@forenglishmore-8tghp.mongodb.net/test?retryWrites=true')
db = client.EnglishMore


app = Flask(__name__)
    
    
def get_word_list():
    decker = request.args.get('decker')
    try:
        word_list = db.wordsList.find({ "deckerInfo" : decker}) # 
        before_clean = dumps(word_list)
        after_clean = before_clean.pop("deckerInfo")
    
        return after_clean
    except Exception:
        return dumps({'error' : str(e)})


from flask import Flask
from flask import request
from pymongo import MongoClient
from bson.json_util import dumps
import json


client = MongoClient('mongodb+srv://xinyi:ZXCVzxcv9859@forenglishmore-8tghp.mongodb.net/test?retryWrites=true')
db = client.EnglishMore
topicList = ["GRE","TOEFL"]
ans = {}
for eachTopic in topicList:
    try:
        topicTotal = 0
        topicNum = db.wordsList.find({"deckerInfo":{'$regex':'^'+eachTopic}}).count()
        ans[eachTopic+"Deckernum"] = topicNum
        print(topicNum)
        for eachList in range(topicNum):
            
            currentList = dumps(db.wordsList.find({"deckerInfo": eachTopic+"Decker"+str(eachList+1)}))
        
            
            currentList = json.loads(currentList)
    
            body = currentList[0]
            
            numPerDecker = sum([len(v) for k, v in body.items() if type(v) == list])
            ans[eachTopic+"Decker"+str(eachList+1)] = numPerDecker
            topicTotal = topicTotal + numPerDecker
        ans[eachTopic+"TotalWords"] = topicTotal
    
    except Exception:
        print(str(Exception))
        


with open('topicAndDeckerInfo.json', 'w') as outfile:  
    json.dump(ans, outfile,indent=4)



import json

with open('topicAndDeckerInfo.json') as json_file:  
    data = json.load(json_file)
    data = dumps(data)
    
    
import urllib3
import json

http = urllib3.PoolManager()
words = http.request('GET', 'http://35.178.77.171:5000/get_word_list?decker=GREDecker3')



from flask import Flask
from flask import request
from pymongo import MongoClient
from bson.json_util import dumps
import json
from pymongo import ReturnDocument


client = MongoClient('mongodb+srv://xinyi:ZXCVzxcv9859@forenglishmore-8tghp.mongodb.net/test?retryWrites=true')
db = client.EnglishMore


db.userProgress.find_one_and_update({"username": "Xinyi"},{"$set": {"test": [1,2,3],"test2":[12,2] }})



from flask import Flask
from flask import request
from pymongo import MongoClient
from bson.json_util import dumps
import json
from pymongo import ReturnDocument


client = MongoClient('mongodb+srv://xinyi:ZXCVzxcv9859@forenglishmore-8tghp.mongodb.net/test?retryWrites=true')
db = client.EnglishMore

password = "ZXCVzxcv985"
username = "Xinyi"
returnMessage = db.userProgress.find({"username" : username})

#   print(dumps(returnMessage))

returnMessage = json.loads(dumps(returnMessage))

if(len(returnMessage) == 0 ):
    print("new user!")
    db.userProgress.insert_one({"username":username,"password":password})
else:
    returnMessage = json.loads(dumps(returnMessage))[0]
    if(returnMessage['password'] != password):
        print("password is wrong")
    else:
        print(returnMessage)



@app.route("/get_progress", methods = ['GET'])
def get_progress():
    username = request.args.get('username')
    password = request.args.get('password')
    try:
        returnMessage = db.userProgress.find({"username" : username})
        returnMessage = json.loads(dumps(returnMessage))

        if(len(returnMessage) == 0 ):
            return "new user"
            db.userProgress.insert_one({"username":username,"password":password})
        else:
            returnMessage = returnMessage[0]
            if(returnMessage['password'] != password):
                return "password is wrong"
            else:
                return dumps(returnMessage)
            
    except Exception, e:
        return dumps({'error' : str(e)})






from flask import Flask
from flask import request
from pymongo import MongoClient
from bson.json_util import dumps
import json
from pymongo import ReturnDocument


client = MongoClient('mongodb+srv://xinyi:ZXCVzxcv9859@forenglishmore-8tghp.mongodb.net/test?retryWrites=true')
db = client.EnglishMore


info = json.loads(dumps(db.topicAndDeckerInfo.find()))
info = info[0]

info.pop('_id')

