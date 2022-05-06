export interface keyValPair{
    key: string;
    val: string;
}

export interface notificationObj{
    title: string ;
    timeVal: number;
}

export interface alert{
    subsystem: "Drivetrain" | "Shooter" | "Intake - Injector";
    severity: "medium" | "high";
}

export interface board{
    id: number;
    x: number;
    y: number;
    height: number;
    width: number;
    isPreset: boolean;
    presetType: string;
    displayType: "number" | "string" | "graph" | "img" | "numberLine";
    additionalData: keyValPair[];
}

export interface cameraData{
    ip: string,
    name: string
}