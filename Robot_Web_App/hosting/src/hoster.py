# Python 3 server example
from http.server import BaseHTTPRequestHandler, HTTPServer
import time
import json
from networktables import NetworkTables

class MyServer(BaseHTTPRequestHandler):
    def do_GET(self):
        print("Gotten?")
        ip = "10.75.61.2"
        NetworkTables.initialize(server=ip)
        dataDict = {}
        sd = NetworkTables.getTable("SmartDashboard")
        for entry in sd.getKeys():
            print(entry)
            dataDict[entry] = NetworkTables.getTable("SmartDashboard").getEntry(entry).getDouble(10)
        self.send_response(200)
        self.send_header("Content-type", "text/plain")
        self.end_headers()
        self.wfile.write(bytes(json.dumps(dataDict, separators=(',', ':')), "ASCII"))


def connectionListener():
    print("CONNECTED TO ROBOT");


NetworkTables.addConnectionListener(connectionListener, immediateNotify=True)


if __name__ == "__main__":
    hostName = "localhost"
    serverPort = 8080
    webServer = HTTPServer((hostName, serverPort), MyServer)
    print("Server started http://%s:%s" % (hostName, serverPort))

    try:
        webServer.serve_forever()
    except KeyboardInterrupt:
        pass

    webServer.server_close()
    print("Server stopped.")
    

