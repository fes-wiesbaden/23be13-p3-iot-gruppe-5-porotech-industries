import { Type } from "@angular/core";

export interface Widget {
    id: number;
    label: string;
    content: Type<any>;
    rows?: number;
    columns?: number;
}