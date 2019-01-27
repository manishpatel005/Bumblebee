import smartcar
from flask import Flask, redirect, request, jsonify, url_for
from flask_cors import CORS

import os

app = Flask(__name__)
CORS(app)

# global variable to save our access_token
access = None

# TODO: Authorization Step 1a: Launch Smartcar authorization dialog
client = smartcar.AuthClient(
    client_id="68aac7cb-e318-4369-bf78-e6d6d902b129",
    client_secret="38f4b3c6-c39e-42e6-85aa-8fcbef35bd08",
    redirect_uri="sc68aac7cb-e318-4369-bf78-e6d6d902b129://exchange",
    scope=['read_vehicle_info','control_security','read_odometer','read_location'],
    test_mode=True,
)



@app.route('/exchange', methods=['GET'])
def exchange():
    # TODO: Authorization Step 3: Handle Smartcar response
    code = request.args.get('code')
    
    print(code)
   
    # TODO: Request Step 1: Obtain an access token
    global access 
   
    access = client.exchange_code(code) 
    return '', 200


@app.route('/vehicle', methods=['GET'])
def vehicle():
    # TODO: Request Step 2: Get vehicle ids
        global access
    
        vehicle_ids = smartcar.get_vehicle_ids(access['access_token'])['vehicles']

        # TODO: Request Step 3: Create a vehicle
        vehicle = smartcar.Vehicle(vehicle_ids[0],access['access_token'])    
        # TODO: Request Step 4: Make a request to Smartcar API
        info = vehicle.info()

        print(info)
        # Unlock the vehicle
      #  status = vehicle.unlock()
       # print("status=",status)
        # Print the odometer reading
       # odometerReading = vehicle.odometer();
        #print("odometer reading=",odometerReading); 

        return jsonify(info)

        
@app.route('/unlock', methods=['GET'])
def unlock():
    # TODO: Request Step 2: Get vehicle ids
        global access
    
        vehicle_ids = smartcar.get_vehicle_ids(access['access_token'])['vehicles']

        # TODO: Request Step 3: Create a vehicle
        vehicle = smartcar.Vehicle(vehicle_ids[0],access['access_token'])    
        # TODO: Request Step 4: Make a request to Smartcar API
        #Unlock the vehicle
        status = vehicle.unlock()
        print("status=",status)
        # Print the odometer reading
       # odometerReading = vehicle.odometer();
        #print("odometer reading=",odometerReading); 

        return 'success'
        
@app.route('/lock', methods=['GET'])
def lock():
    # TODO: Request Step 2: Get vehicle ids
        global access
    
        vehicle_ids = smartcar.get_vehicle_ids(access['access_token'])['vehicles']

        # TODO: Request Step 3: Create a vehicle
        vehicle = smartcar.Vehicle(vehicle_ids[0],access['access_token'])    
        # TODO: Request Step 4: Make a request to Smartcar API
        #Unlock the vehicle
        status = vehicle.lock()
        print("status=",status)
        # Print the odometer reading
       # odometerReading = vehicle.odometer();
        #print("odometer reading=",odometerReading); 

        return 'success'
        
@app.route('/getLocation', methods=['GET'])
def getLocation():
    # TODO: Request Step 2: Get vehicle ids
        global access
    
        vehicle_ids = smartcar.get_vehicle_ids(access['access_token'])['vehicles']

        # TODO: Request Step 3: Create a vehicle
        vehicle = smartcar.Vehicle(vehicle_ids[0],access['access_token'])    
        # TODO: Request Step 4: Make a request to Smartcar API
        #Unlock the vehicle
        response = vehicle.location()
        print(response)
        # Print the odometer reading
       # odometerReading = vehicle.odometer();
        #print("odometer reading=",odometerReading); 

        return jsonify(response)

if __name__ == '__main__':
    app.run(port=8000)
