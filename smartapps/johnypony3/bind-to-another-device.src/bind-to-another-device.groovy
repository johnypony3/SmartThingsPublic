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

final SWITCH_LEVEL = "Switch Level"

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

  //are the switches dimmable? if so, lets bind that also
  Boolean switch1_Level_Capable = switch1.capabilities.name.contains(SWITCH_LEVEL)
  Boolean switch2_Level_Capable = switch2.capabilities.name.contains(SWITCH_LEVEL)

  if (switch1_Level_Capable && switch2_Level_Capable){
    subscribe(switch1, "switch.level", switchLevelHandler)
    subscribe(switch2, "switch.level", switchLevelHandler)
  } else {
  	log.debug "incapable"
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

def switchLevelHandler(evt) {
  //switch2.setLevel("switch")
  log.debug "current switch value: ${device.currentValue('switch')}"
}
