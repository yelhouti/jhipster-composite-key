import { NgModule } from '@angular/core';

import { CompositekeySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [CompositekeySharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [CompositekeySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class CompositekeySharedCommonModule {}
