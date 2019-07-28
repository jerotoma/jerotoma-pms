export class LocalStorage {

  store(key: string, value: string): void {
    localStorage.setItem(key, value);
  }

  getValue(key: string): string {
    return localStorage.getItem(key);
  }

  clear(): void {
    localStorage.clear();
  }

  remove(key: string): void {
    localStorage.removeItem(key);
  }
}
