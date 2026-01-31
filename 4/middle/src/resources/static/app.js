const API = '';

function getCookie(name) {
    const v = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return v ? v[2] : null;
}

function fetchApi(path, options = {}) {
    const token = getCookie('token');
    const headers = { 'Content-Type': 'application/json', ...options.headers };
    if (token) headers['Authorization'] = 'Bearer ' + token;
    return fetch(API + path, { ...options, headers, credentials: 'include' });
}

function fileToBase64(file) {
    return new Promise((resolve, reject) => {
        const r = new FileReader();
        r.onload = () => resolve(r.result);
        r.onerror = reject;
        r.readAsDataURL(file);
    });
}

function base64ToDataUrl(base64) {
    if (!base64) return null;
    if (base64.startsWith('data:')) return base64;
    return 'data:image/jpeg;base64,' + base64;
}
