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
        section("Dimmers") {
          paragraph "For dimmers a leader dimmer needs to be set, without this, you get an interesting light show."
          input "dimmer_Leader", "capability.switchLevel", title:"leader?", multiple: true, required: false
          input "dimmers", "capability.switchLevel", title:"dimmers?", multiple: true, required: false
        }
        section("Switches") {
                input "switches", "capability.switch", title:"switches?", multiple: true, required: false
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
        subscribe(dimmer_Leader, "level", switchLevelHandler)
        subscribe(dimmer_Leader, "switch", switchLevelHandler)
        subscribe(switches, "switch", switchHandler)
}

def switchHandler(evt) {
        log.debug "device: ${evt.device} value: ${evt.value}"

        if (!switches) {
                return
        }

        if (evt.value == "on") {
                switches.on()
        }

        if (evt.value == "off") {
                switches.off()
        }
}

def switchLevelHandler(evt) {
        log.debug "device: ${evt.device} value: ${evt.value}"

        if (!dimmers) {
                return
        }

        if (evt.value == "on") {
                dimmers.on()
        }

        if (evt.value == "off") {
                dimmers.off()
        }

        if (evt.value.isNumber()) {
                dimmers.setLevel(evt.value)
        }
}
