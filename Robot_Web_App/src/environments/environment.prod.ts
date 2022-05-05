export const environment = {
  production: false,
  versionNumber: "0.7.1",
  defaultURL: "http://localhost:3000/robot_info",
  sendURL: "http://localhost:3000/to_robot",
  defaultPollingRate: 1000,
  matchTime: 135,
  autoList: ["Auto_2_Ball_A", "Auto_2_Ball_B", "Auto_3_Ball", "Auto_Do_Nothing"],
  notificationList: [{"title": "Begin Climb", "timeVal": 20}],
  driveModes: [
    "ULTIMATESWERVE",
    "ULTIMATEDEFENCE",
    "ROBOTCENTRICSWERVE",
    "TANK",
    "SNAKE",
    "SNAKE_X",
    "CRAB",
    "CRAB_X",
    "SPIN",
    "CAR",
    "CAR_X",
    "TANK_X",
    "BALL_TRACK",
    "HUB_TRACK"
  ],
  intakeModes: [
    "INTAKE_DEPLOY_REQUESTED",
    "INTAKE_RETRACT_REQUESTED",
    "INTAKE_DEPLOYED",
    "INTAKE_RETRACTED"
  ],
  injectorModes: [
    "INJECTOR_REVERSE",
    "INJECTOR_FORWARD",
    "INJECTOR_INDEX_BALL",
    "INJECTOR_REVERSE_INDEX_BALL",
    "INJECTOR_STOP"
  ]
};

