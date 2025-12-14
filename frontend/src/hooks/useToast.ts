import { toast, ToastOptions } from "react-toastify";

export function useToast() {
  const defaultOptions: ToastOptions = {
    position: "top-right",
    autoClose: 3000,
    hideProgressBar: false,
    closeOnClick: true,
    pauseOnHover: true,
    draggable: true,
    theme: "colored",
  };

  function toastSuccess(message: string, options?: ToastOptions) {
    toast.success(message, { ...defaultOptions, ...options });
  }

  function toastError(message: string, options?: ToastOptions) {
    toast.error(message, { ...defaultOptions, ...options });
  }

  function toastInfo(message: string, options?: ToastOptions) {
    toast.info(message, { ...defaultOptions, ...options });
  }

  function toastWarning(message: string, options?: ToastOptions) {
    toast.warn(message, { ...defaultOptions, ...options });
  }

  return { toastSuccess, toastError, toastInfo, toastWarning };
}
