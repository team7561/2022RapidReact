from flask import Flask, send_from_directory, jsonify, request
from networktables import NetworkTables 
import random
from flask_cors import CORS


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
        dataDict[entry] = NetworkTables.getTable("SmartDashboard").getEntry(entry).getDouble(0)
    for entry in limelightData.getKeys():
      dataDict[entry] = NetworkTables.getTable("SmartDashboard").getEntry(entry).getDouble(0)
    return jsonify(dataDict)

@app.route("/to_robot")
def sendRobotData():
    key = request.args.get("key")
    val = request.args.get("val")
    sd = NetworkTables.getTable("SmartDashboard")
    try:
      val = float(val)
      sd.putNumber(key, val)
    except TypeError:
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
        "Intake Speed": random.randint(0, 1000),
        "Injector Mode": "idle",
        "Auto": "Auto_3_Ball",
        "Drivetrain Mode": "Crab",
        "Gyro Angle": random.randint(0, 360),
        "visionMode": "Hub Track",
        "Shooter A Speed": 900 + random.randint(0, 100),
        "Shooter A Setpoint": 950,
        "Shooter B Speed": 800 + random.randint(0, 100),
        "Shooter B Setpoint": 950,
        "Battery Voltage": 10.8 + random.random(),
        "ta": random.random() * 20 + 5,
        "tx": random.random() * 20,
        "ty": random.random() * 20,
        "robotX": random.random(),
        "robotY": random.random()
    });


if __name__ == '__main__':
  app.run(host='localhost', port=portNumber, debug=False)

