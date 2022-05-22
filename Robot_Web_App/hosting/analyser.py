# Reads data from a robotData.json file
import matplotlib.pyplot as plt
import json
import csv

# Defines what values are plotted
'''
Special Keywords:
    'Shooter Data A' (or B): Displays setpoints and vals for given shooter
    'vision': Displays the average position and size of vision target
    'drivetrain': Displays the angles of each of the swerve modules
    Alternatively, any smart dashboard key can be inputted and be plotted
'''

valsToPlot = ["Shooter Data A", "Shooter Data B", "vision", "drivetrain"]
filePath = "./robotData/help"

graphCols = 2  # How many columns the plt window will have
graphRows = 2  # How many rows the window will have

if(graphCols * graphRows != len(valsToPlot)):
    print("INCORRECT NUMBER OF VALS TO PLOT WERE PROVIDED")
    print("EXPECTED DATA FOR " + str(graphCols * graphRows) +
          " BUT RECIEVED " + str(len(valsToPlot)))
    exit()


def getGraphData(key, pollingRate, data):
    yVals = []
    xVals = []
    for index, dataPoint in enumerate(data):
        xVals.append((index * pollingRate) / 1000)
        try:
            yVals.append(float(dataPoint[key]))
        except KeyError:
            yVals.append(0)
    return xVals, yVals


# Load Data
with open(filePath + ".json", "r") as robotFile:
    rawData = json.load(robotFile)

# Clean Datadict
robotData = []
for moment in rawData:
    momentDict = eval(moment)
    cleanDataDict = {}
    for valPair in momentDict:
        try:
            cleanDataDict[valPair["key"]] = valPair["val"]
        except KeyError:
            pass
    robotData.append(cleanDataDict)


pollingRate = int(robotData[0]["pollingRate"])
fig, ax = plt.subplots(graphRows, graphCols)

count = 0
letterList = ["A", "B", "C", "D"]
for rowNum in range(graphCols):
    for colNum in range(graphCols):
        setXLabel = True
        if valsToPlot[count][0:-1] == "Shooter Data ":
            shooterType = valsToPlot[count][-1]
            valXVals, valYVals = getGraphData(
                "Shooter " + shooterType + " Speed", pollingRate, robotData)
            setpointXVals, setpointYVals = getGraphData(
                "Shooter " + shooterType + " Setpoint", pollingRate, robotData)
            ax[rowNum, colNum].plot(
                valXVals, valYVals, label="Shooter " + shooterType + " Values")
            ax[rowNum, colNum].plot(
                setpointXVals, setpointYVals, label="Shooter " + shooterType + " Setpoints")
            ax[rowNum, colNum].legend()
        elif valsToPlot[count] == "vision":
            tXVals = getGraphData("tx", pollingRate, robotData)[1]
            tYVals = getGraphData("ty", pollingRate, robotData)[1]
            tAVals = getGraphData("ta", pollingRate, robotData)[1]
            avgTXVal = [sum(tXVals) / len(tXVals)]
            avgTYVal = [sum(tYVals) / len(tYVals)]
            avgTAVal = [sum(tAVals) / len(tAVals) * 100]
            ax[rowNum, colNum].scatter(avgTXVal, avgTYVal, s=avgTAVal)
            ax[rowNum, colNum].set_xlim([-30, 30])
            ax[rowNum, colNum].set_ylim([-30, 30])
            setXLabel = False
        elif valsToPlot[count] == "drivetrain":
            for letter in letterList:
                xVals, yVals = getGraphData(
                    letter + "_Angle", pollingRate, robotData)
                ax[rowNum, colNum].plot(xVals, yVals, label=letter + " Angle")
                ax[rowNum, colNum].legend()

        else:
            xVals, yVals = getGraphData(
                valsToPlot[count], pollingRate, robotData)
            ax[rowNum, colNum].plot(xVals, yVals)

        if setXLabel:
            ax[rowNum, colNum].set_xlabel("Time (Seconds)")
        ax[rowNum, colNum].set_title(valsToPlot[count])
        count += 1

plt.show()

if input("Save ALL data as .csv file (y/n) ").lower() == "y":
    keys = list(robotData[0].keys())
    csvData = []

    for _ in range(len(keys) + 2):
        thisRow = []
        for _ in range(len(robotData) + 1):
            thisRow.append("")
        csvData.append(thisRow)

    csvData[1][0] = "Timing val"

    for i in range(len(robotData)):
        csvData[1][i + 1] = i + 1

    for keyIndex, key in enumerate(keys):
        csvData[keyIndex + 2][0] = key
        for timeIndex, timePeriod in enumerate(robotData):
            csvData[keyIndex + 2][timeIndex + 1] = timePeriod[key]

    with open(filePath + ".csv", "w+", newline='', encoding="UTF-8") as csvFile:
        writer = csv.writer(csvFile)

        writer.writerows(csvData)
    print("Done")
else:
    exit()
