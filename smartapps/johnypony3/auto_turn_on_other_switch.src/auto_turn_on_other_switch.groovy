/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Turn It On For 5 Minutes
 *  Turn on a switch when a contact sensor opens and then turn it back off 5 minutes later.
 *
 *  Author: SmartThings
 */
definition(
    name: "Bind to Another Device",
    namespace: "smartthings",
    author: "SmartThings",
    description: "Mirrors another devices state",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/light_contact-outlet.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/light_contact-outlet@2x.png"
)

preferences {
    section("Which device?") {
		    input "switch1", "capability.switch"
    }
    section("Which other device?") {
        input "switch2", "capability.switch"
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"
  initialize()
}

def updated(settings) {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
  initialize()
}

def initialize(){
  subscribe(switch1, "switch.on", switchOnHandler)
  subscribe(switch2, "switch.on", switchOnHandler)
  subscribe(switch1, "switch.off", switchOffHandler)
  subscribe(switch2, "switch.off", switchOffHandler)
}

def switchOnHandler(evt) {
  if (switch1.currentValue("switch") != "on"){
    switch1.on()
  }
  if (switch2.currentValue("switch") != "on"){
    switch2.on()
  }
}

def switchOffHandler(evt) {
  if (switch1.currentValue("switch") != "off"){
    switch1.off()
  }
  if (switch2.currentValue("switch") != "off"){
    switch2.off()
  }
}
