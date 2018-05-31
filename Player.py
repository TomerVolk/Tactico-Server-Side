import Tool


class Player:
    def __init__(self, is_me):
        self.isMe = is_me
        self.tools = []
        self.tools[0] = Tool(11)
        for i in range(1, 7, 1):
            self.tools[i] = Tool(0)
        self.tools[7] = Tool(1)
        for i in range(8, 16, 1):
            self.tools[i] = Tool(2)
        for i in range(16, 21, 1):
            self.tools[i] = Tool(3)
        for i in range(21, 25, 1):
            self.tools[i] = Tool(4)
        for i in range(25, 29, 1):
            self.tools[i] = Tool(5)
        for i in range(29, 33, 1):
            self.tools[i] = Tool(6)
        for i in range(33, 36, 1):
            self.tools[i] = Tool(7)
        for i in range(36, 38, 1):
            self.tools[i] = Tool(8)
        self.tools[38] = Tool(9)
        self.tools[39] = Tool(10)

    def set_tool(self, x, y, i):
        self.tools[i].place = (x, y)
