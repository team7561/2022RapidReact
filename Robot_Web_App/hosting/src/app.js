var express = require("express");
var cors = require("cors");
var compression = require("compression");
var path = require("path");
var app = express();
var webPagePort = 4000;
var backEndTestPort = 3000;
// Middleware to use
app.use(cors({
    origin: "*",
    credentials: true,
    optionSuccessStatus: 200
}));
app.use(compression());
// Serve the webpage
app.get('*.*', express.static(path.join(__dirname, "../public/")));
app.get('/', function (req, res) {
    res.status(200).sendFile("/", { root: path.join(__dirname, "../public/") });
});
app.listen(webPagePort, function () {
    console.log("Front end server is listening at http://localhost:" + webPagePort.toString());
});
// Serve test backend
app.get('/robot_info', function (req, res) {
    res.send(JSON.stringify({
        "A_Angle": Math.round(Math.random() * 360),
        "B_Angle": Math.round(Math.random() * 360),
        "C_Angle": Math.round(Math.random() * 360),
        "D_Angle": Math.round(Math.random() * 360),
        "A_Offset_Angle": 0.810631,
        "B_Offset_Angle": -0.368438,
        "C_Offset_Angle": -0.78405,
        "D_Offset_Angle": 0.6279069,
        "Intake Speed": Math.round(Math.random() * 1000),
        "Injector Mode": "idle",
        "Auto": "Auto_3_Ball",
        "Drivetrain Mode": "Crab",
        "Gyro Angle": Math.round(Math.random() * 360),
        "visionMode": "Hub Track",
        "Shooter A Speed": 900 + Math.random() * 100,
        "Shooter A Setpoint": 950,
        "Shooter B Speed": 800 + Math.random() * 100,
        "Shooter B Setpoint": 950,
        "Battery Voltage": 10.8 + (Math.random()),
        "ta": Math.random() * 20 + 5,
        "tx": Math.random() * 20,
        "ty": Math.random() * 20,
        "robotX": Math.random(),
        "robotY": Math.random()
    }));
});
app.listen(backEndTestPort, function () {
    console.log("Test Backend Server is listening on the port: ".concat(backEndTestPort));
});
