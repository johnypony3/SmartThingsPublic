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
 *  Turn It On For 15 Minutes
 *  When a switch is turned on, it will turn off 15 minutes later.
 *
 *  Author: johnypony3
 */
definition(
  name: "Turn It On For 15 Minutes",
  namespace: "johnypony3",
  author: "johnypony3",
  description: "When a switch is turned on, it is then turned off after 15 minutes.",
  category: "Green Living",
  iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/light_contact-outlet.png",
  iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/light_contact-outlet@2x.png"
)

preferences {
  section("Which switch?") {
    input "timed_Switch", "capability.switch", title: "Switch",  multiple: false, required: true
  }
  section("How many minutes?") {
    input "minutes", "number", required: true, title: "How many minutes?", defaultValue: 15
  }
  section("Misc"){
    icon(title: "Pick an icon", required: true)
  }
}

def subscribe(){
  subscribe(timed_Switch, "switch.on", timed_Switch_On_Handler)
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	subscribe()
}

def updated(settings) {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	subscribe()
}

def timed_Switch_On_Handler(evt) {
	timed_Switch.on()
	def delay = 60 * settings.minutes
	runIn(delay, timed_Switch_Off_Handler)
}

def timed_Switch_Off_Handler() {
	timed_Switch.off()
}
