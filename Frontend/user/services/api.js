// user/services/api.js
const API_BASE = "http://localhost:8080/api";
const IMAGE_BASE = "http://localhost:8080/uploads/";

/**
 * Build usable image URL from backend response field.
 * - Accepts if backend returns full URL or just filename.
 */
function buildImageUrl(imageField) {
  if (!imageField) return null;
  if (imageField.startsWith("http://") || imageField.startsWith("https://")) {
    return imageField;
  }
  return IMAGE_BASE + imageField;
}

/** Generic fetch helper for GET */
async function fetchJson(path) {
  const res = await fetch(`${API_BASE}${path}`);
  if (!res.ok) {
    const text = await res.text();
    throw new Error(`Fetch ${path} failed: ${res.status} ${text}`);
  }
  return res.json();
}
