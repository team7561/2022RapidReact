from flask import Flask, send_from_directory, jsonify, request
from networktables import NetworkTables 
import random
import socket
from flask_cors import CORS
from scipy import rand


doPublicHosting = False
if(doPublicHosting):
  hostingName = socket.gethostbyname(socket.gethostname())
else:
  hostingName = "localhost"

app = Flask("Robot Wep app")
CORS(app)
robotIP = "10.75.61.2"
NetworkTables.initialize(server=robotIP)
portNumber = 3000

@app.route("/robot_info") # Get robot data 
def handleRobotData():
    dataDict = {"sucess": True}
    sd = NetworkTables.getTable("SmartDashboard")
    limelightData = NetworkTables.getTable("limelight")
    for entry in sd.getKeys():
      val = sd.getEntry(entry).getDouble(9876)
      if(val == 9876):
        dataDict[entry] = sd.getEntry(entry).getString("")
      else:
        dataDict[entry] = val
    for entry in limelightData.getKeys():
      val = limelightData.getEntry(entry).getDouble(9876)
      if(val == 9876):
        dataDict[entry] = limelightData.getEntry(entry).getString("")
      else:
        val = float(val)
        dataDict[entry] = val
    return jsonify(dataDict)

@app.route("/to_robot")
def sendRobotData():
    key = request.args.get("key")
    val = request.args.get("val")
    print("RECIEVED DATA " + str({key: val}))
    sd = NetworkTables.getTable("SmartDashboard")
    try:
      val = float(val)
      sd.putNumber(key, val)
    except(TypeError, ValueError):
      print("Exception reached")
      sd.putString(key, val)

    return jsonify({"status": 200})

@app.route('/')
def root(): # Send the html file
  return send_from_directory('./public', 'index.html')

# Returns requested file (Allows for angular to get external .js and .css files)
@app.route('/<path:path>', methods=['GET'])
def static_proxy(path):
  return send_from_directory('./public', path)


# Test Backend
@app.route("/test") # Get robot data 
def testBackend():
    return jsonify({
        "A_Angle": random.randint(0, 360),
        "B_Angle": random.randint(0, 360),
        "C_Angle": random.randint(0, 360),
        "D_Angle": random.randint(0, 360),
        "A_Offset_Angle": 0.810631,
        "B_Offset_Angle": -0.368438,
        "C_Offset_Angle": -0.78405,
        "D_Offset_Angle": 0.6279069,
        "Intake Mode": "INTAKE_RETRACTED",
        "Intake Speed": random.randint(-500, 500),
        "Injector Speed": random.randint(-100, 100),
        "Injector Mode": "INJECTOR_REVERSE",
        "Auto": "Auto_3_Ball",
        "Drivetrain Mode": "ULTIMATESWERVE",
        "Gyro Angle": random.randint(0, 360),
        "visionMode": "Hub Track",
        "Shooter A Speed": random.randint(1700, 1800),
        "Shooter A Setpoint": random.randint(1700, 1800),
        "Shooter B Speed": 800 + random.randint(0, 100),
        "Shooter B Setpoint": random.randint(1700, 1800),
        "Battery Voltage": 10.8 + random.random(),
        "ta": random.random() * 20 + 5,
        "tx": random.random() * 20,
        "ty": random.random() * 20,
        "robotX": random.random(),
        "robotY": random.random()
    });


if __name__ == '__main__':
  app.run(host=hostingName, port=portNumber, debug=False)

