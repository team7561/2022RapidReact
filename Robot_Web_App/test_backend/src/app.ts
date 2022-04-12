const express = require('express');
const cors = require('cors');

const app = express();
const port: number = 3000;

app.use(cors({
    origin: "*",
    credentials: true,
    optionSuccessStatus: 200,
}))

app.get('/robot_info', (req: any, res: any) => {
    res.send(JSON.stringify({
        "A_Angle": Math.round(Math.random() * 360),
        "B_Angle": Math.round(Math.random() * 360),
        "C_Angle": Math.round(Math.random() * 360),
        "D_Angle": Math.round(Math.random() * 360),
        "A_Offset_Angle": 0.810631,
        "B_Offset_Angle": -0.368438,
        "C_Offset_Angle": -0.78405,
        "D_Offset_Angle": 0.6279069,
        "Intake Speed":  Math.round(Math.random() * 1000),
        "Injector Mode": "idle",
        "Auto": "Auto_3_Ball",
        "Drivetrain Mode": "Crab",
        "Gyro Angle": Math.round(Math.random() * 360),
        "visionMode": "Hub Track",  
        "Shooter A Speed": 900 + Math.random() * 100,
        "Shooter A Setpoint": 950,
        "Shooter B Speed": 900 + Math.random() * 100,
        "Shooter B Setpoint": 950,
        "Battery Voltage": 10.8 + (Math.random())
    }))
});


app.listen(port, () => {
    console.log(`Server listening on the port: ${port}`);
});
