class Tool:

    def __init__(self, tool_type):
        self.tool_type = tool_type
        self.place = (-1, -1)
        self.isDead = True

    def start(self, x, y):
        self.place = (x, y)
        self.isDead = False

    def kill(self):
        self.isDead = True
        self.place = (-1, -1)

    def __str__(self):
        return self.type + "##" + self.place[0] + "##" + self.place[1] + "##" +self.isDead
