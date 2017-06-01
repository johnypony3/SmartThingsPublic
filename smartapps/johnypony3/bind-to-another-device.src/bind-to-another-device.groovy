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
  initialize()
}

def updated(settings) {
  unsubscribe()
  initialize()
}

def initialize(){
	subscribe(switch1, "switch", switchHandler)
    subscribe(switch2, "switch", switchHandler)

/*
  //are the switches dimmable? if so, lets bind that also
  Boolean switch1_Level_Capable = switch1.capabilities.name.contains("Switch Level")
  Boolean switch2_Level_Capable = switch2.capabilities.name.contains("Switch Level")

  if (switch1_Level_Capable && switch2_Level_Capable){
  	log.debug "capable"

    subscribe(switch1, "switch.level", switchLevelHandler)
    subscribe(switch2, "switch.level", switchLevelHandler)
  } else {
  	log.debug "incapable"
  }
  */
}



def switchHandler(evt) {
/*

  if (switch1.currentValue("switch") != evt.value) {
  	? switch1.on() : switch1.off()
  }
    */
log.debug "The value of this event is ${evt.value}"
log.debug "The device of this event is ${evt.device}"
log.debug "The value of this event is different from its previous value: ${evt.isStateChange()}"

if (evt.value == "on") {
          if (switch1.currentValue("switch") != "on"){
            switch1.on()
          }
          if (switch2.currentValue("switch") != "on"){
            switch2.on()
          }
    } else if (evt.value == "off") {
          if (switch1.currentValue("switch") != "off"){
            switch1.off()
          }
          if (switch2.currentValue("switch") != "off"){
            switch2.off()
          }
    }
}

def switchLevelHandler(evt) {
  //switch2.setLevel("switch")
  log.debug "current switch value: ${device.currentValue('switch')}"
}
