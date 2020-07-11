import { Injectable, inject } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {
  constructor() {}

  store(key: string, value: string): void {
    window.localStorage.setItem(key, value);
  }

  getValue(key: string): string {
    return window.localStorage.getItem(key);
  }

  clear(): void {
    window.localStorage.clear();
  }

  remove(key: string): void {
    window.localStorage.removeItem(key);
  }

}
