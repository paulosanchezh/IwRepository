import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-area.js';
import '@vaadin/vaadin-select/src/vaadin-select.js';
import '@vaadin/vaadin-list-box/src/vaadin-list-box.js';
import '@vaadin/vaadin-item/src/vaadin-item.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';
import '@vaadin/vaadin-date-picker/src/vaadin-date-picker.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';

class CogerCitas extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="width: 100%; height: 100%;">
 <vaadin-horizontal-layout class="header" style="width: 100%; flex-basis: var(--lumo-size-l); flex-shrink: 0; background-color: #F5A4DE80; align-self: center; align-items: baseline; justify-content: center;" theme="spacing-xl">
  <h4 style="align-self: center;">Grupo 7 IW</h4>
 </vaadin-horizontal-layout>
 <vaadin-vertical-layout class="content" style="width: 100%; flex-grow: 1; flex-shrink: 1; flex-basis: auto; background-color: #4388CC60;">
  <vaadin-text-area label="Coger cita" placeholder="Rellene el formulario para coger sus citas" style="align-self: stretch;"></vaadin-text-area>
  <vaadin-form-layout id="vaadinFormLayout">
   <vaadin-select value="Item one" label="Centro">
    <template>
     <vaadin-list-box selected="0">
      <vaadin-item selected>
        La paz 
      </vaadin-item>
      <vaadin-item>
        Puerta del Mar 
      </vaadin-item>
      <vaadin-item>
        San Vicente 
      </vaadin-item>
     </vaadin-list-box>
    </template>
   </vaadin-select>
   <vaadin-select value="Item one" label="Localidad">
    <template>
     <vaadin-list-box>
      <vaadin-item selected>
        San Fernando 
      </vaadin-item>
      <vaadin-item>
        Cadiz 
      </vaadin-item>
      <vaadin-item>
        Chiclana 
      </vaadin-item>
     </vaadin-list-box>
    </template>
   </vaadin-select>
   <vaadin-select value="item one" label="Especialidad">
    <template>
     <vaadin-list-box selected="0">
      <vaadin-item selected>
        Medico de cabecera 
      </vaadin-item>
      <vaadin-item>
        Psicólogo 
      </vaadin-item>
      <vaadin-item>
        Fisio 
      </vaadin-item>
     </vaadin-list-box>
    </template>
   </vaadin-select>
   <vaadin-select value="Item one" style="flex-grow: 0;" label="Provincia">
    <template>
     <vaadin-list-box>
      <vaadin-item selected>
        Cadiz 
      </vaadin-item>
      <vaadin-item>
        Malaga 
      </vaadin-item>
      <vaadin-item>
        Malaga 
      </vaadin-item>
     </vaadin-list-box>
    </template>
   </vaadin-select>
   <vaadin-date-picker label="Fecha Cita" placeholder="Esocoge una fecha" style="flex-grow: 0; align-self: stretch; width: 100%;"></vaadin-date-picker>
   <div></div>
   <vaadin-button style="align-self: center; margin: var(--lumo-space-l);">
     Confirmar cita 
   </vaadin-button>
  </vaadin-form-layout>
 </vaadin-vertical-layout>
 <vaadin-horizontal-layout class="footer" style="width: 100%; flex-basis: var(--lumo-size-l); flex-shrink: 0; background-color: #F5A4DE80; justify-content: center;">
  <h4 style="align-self: center;">footer </h4>
 </vaadin-horizontal-layout>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'coger-citas';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(CogerCitas.is, CogerCitas);
