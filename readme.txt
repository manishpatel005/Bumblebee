README

This project named BumbleBee is a part of TAMUHACK 2019. We the developers- Liam, Troy, Manish and Nikhil, worked on SMARTCAR to enable the users to do lot of cool stuff with their car such as Locking/Unlocking remotely, Finding your parked car etc.

REQUIREMENTS

To run this project you should have the following libraries installed:
1. Serveo
2. Python 2.7 or above

HOW TO RUN:
1. Go to the tutorial folder "smartcar/getting-started-python-sdk/tutorial"
2. Run: pip install -r requirements.txt
3. Add the CLIENT_ID, CLIENT_SECRET, and REDIRECT_URI given on smartcar.com website as follows:

export CLIENT_ID=<key>
export CLIENT_SECRET=<secret>
export REDIRECT_URI=http://localhost:8000/exchange

4. Run:  python main.py
5. Run the serveo server from folder of your choice : ssh -R 80:localhost:8000 serveo.net
6. Run the app on your phone with your credentials 
