export const getFileDataFromInput = (event) => {
  return event.target.files[0];
};

export const isFileSizeOk = (file) => {
  return file.size < parseInt(import.meta.env.VITE_MAX_FILE_SIZE);
};

export const encodeImageToBase64 = (file) => {
  return new Promise((resolve, reject) => {
    let reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = (event) => {
      resolve(event.target.result);
    };
    reader.onerror = (error) => {
      reject(error);
    };
  });
};

export const getFileName = (file) => {
  return file.name;
};
