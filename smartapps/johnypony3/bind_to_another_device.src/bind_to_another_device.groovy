/**
 *  Copyright 2017 johnypony3
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
 *  Bind to Another Device
 *  Mirrors another devices state.
 *
 *  Author: johnypony3
 */
definition(
    name: "Bind to Another Device",
    namespace: "johnypony3",
    author: "johnypony3",
    description: "Mirrors another devices state",
    category: "Green Living",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/light_contact-outlet.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/light_contact-outlet@2x.png"
)

preferences {
    section("Which switch?") {
		    input "switch1", "capability.switch"
    }
    section("Which other switch?") {
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

  def mySwitchCaps = switch1.capabilities

  mySwitchCaps.each {cap ->
      log.debug "Capability name: ${cap.name}"
      cap.attributes.each {attr ->
          log.debug "-- Attribute name; ${attr.name}"
      }
  }
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
