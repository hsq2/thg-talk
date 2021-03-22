export const formatTime = (date: Date) => {
  return (
    (date.getHours() < 10 ? "0" : "") +
    date.getHours() +
    ":" +
    (date.getMinutes() < 10 ? "0" : "") +
    date.getMinutes()
  );
};

export const formatDate = (date: Date) => {
  return (
    (date.getDate() < 10 ? "0" : "") +
    date.getDate() +
    "/" +
    (date.getMonth() < 10 ? "0" : "") +
    (date.getMonth() + 1) +
    "/" +
    date.getFullYear()
  );
};
