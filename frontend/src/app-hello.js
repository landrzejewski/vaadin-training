import {html, PolymerElement} from "@polymer/polymer/polymer-element";

class AppHello extends PolymerElement {

    static get template() {
        return html`
            <style>
                .text {
                    color: cadetblue;
                }
            </style>
            <vaadin-button id="hello-btn" on-click="sayHello">Say hello</vaadin-button>
            <h2 class="text">{{name}}</h2>
        `;
    }

    sayHello() {
        this.name = "Hello JS";
    }

}

window.customElements.define('app-hello', AppHello);