export type MessageRequest = {
  senderId: number;
  connectionId: number;
  content: string;
};

export type MessageResponse = {
  senderId: number;
  receiverId: number;
  connectionId: number;
  content: string;
};
