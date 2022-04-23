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