import { HttpStatusCode } from "@angular/common/http";

export interface response<T> {
    status: HttpStatusCode;
    data: T | null;
    error?: string;
}
