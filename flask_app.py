from flask import Flask, jsonify
from flask import request
from pymongo import MongoClient
from bson.json_util import dumps
import json


client = MongoClient('mongodb+srv://xinyi:ZXCVzxcv9859@forenglishmore-8tghp.mongodb.net/test?retryWrites=true')
db = client.EnglishMore


app = Flask(__name__)

# @app.route("/add_contact", methods = ['POST'])
# def add_contact():
#     try:
#         data = json.loads(request.data)
#         user_name = data['name']
#         user_contact = data['contact']
#         if user_name and user_contact:
#             status = db.Contacts.insert_one({
#                 "name" : user_name,
#                 "contact" : user_contact
#             })
#         return dumps({'message' : 'SUCCESS'})
#     except Exception, e:
#         return dumps({'error' : str(e)})

@app.route("/get_all_topic", methods = ['GET'])
def get_all_contact():
    try:
        topicsData = db.topicData.find()
        return dumps(topicsData)
    except Exception, e:
        return dumps({'error' : str(e)})



@app.route("/get_word_list", methods = ['GET'])
def get_word_list():
    decker = request.args.get('decker')
    try:
        word_list = db.wordsList.find({ "deckerInfo" : decker}) 
        word_dict = json.loads(dumps(word_list))
	word_dict = word_dict[0]
        word_dict.pop('deckerInfo')
	word_dict.pop('_id')
        return jsonify(word_dict)
    except Exception, e:
	return dumps({'error' : str(e)})
        

@app.route("/getTopicAndDeckerInfo")
def getTopicAndDeckerInfo():
    
    try:
	info = json.loads(dumps(db.topicAndDeckerInfo.find()))
	info = info[0]
	info.pop('_id')
        return dumps(info)
    except Exception, e:
        return dumps({'error' : str(e)})

@app.route("/ifUpdate")
def ifUpdata():
    return "true"



@app.route("/post_progress", methods = ['POST'])
def post_progress():
    try:
        data = json.loads(request.data)
        username = data['username']
        topic = data['topic']
        topicProgress = data['topicProgress']
        deckerName = data['deckerName']
        deckerProgress = data['deckerProgress']
        deckerNum = data['deckerNum']
        db.userProgress.find_one_and_update({"username": username},{"$set": {topic:topicProgress, deckerName:deckerProgress, deckerName+"num":deckerNum}})
        return dumps({'message' : 'SUCCESS'})
    except Exception, e:
        return dumps({'error' : str(e)})	


@app.route("/get_progress", methods = ['GET'])
def get_progress():
    username = request.args.get('username')
    password = request.args.get('password')
   # username = "Xinyi"
   # password = "ZXCVzxcv9859"
    try:
        returnMessage = db.userProgress.find({"username" : username})
        returnMessage = json.loads(dumps(returnMessage))

        if(len(returnMessage) == 0 ):
	    db.userProgress.insert_one({"username":username,"password":password})
	    return "new user"
        else:
            returnMessage = returnMessage[0]
            if(returnMessage['password'] != password):
                return "password is wrong"
            else:
		returnMessage.pop('username')
		returnMessage.pop('password')
		returnMessage.pop('_id')
                return dumps(returnMessage)
            
    except Exception, e:
        return dumps({'error' : str(e)})
