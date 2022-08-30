# TicketSystem
### Configuration
- Add discord token to config.json
- Add role id's to config.json
- Add category id's to config.json
- Mark Java directory as: Sources Root
- Mark resources directory as: Resources Root

### Getting Started
- Run the application
- Type !ticket in your stagingArea

![image](https://user-images.githubusercontent.com/54646452/187344298-a1817766-a31d-40ce-8a97-d3ed66dc3174.png)
- Here you can choose which ticket should be created.

### Closing Ticket
#### Members with Staff Privileges in config.json
- Upon interacting with the Close button you are presented with 3 options:
- **Delete** Deletes the ticket.
- **Move Ticket** - Moves the ticket to closed and delivers a transcript.
- **Cancel** - Cancels the most recent interaction and deletes the message.
#### Members **Without** Staff Privileges in config.json
- Upon interacting with the Close button you are presented with 2 options:
- **Delete** - Deletes the ticket.
- **Cancel** - Cancels the most recent interaction and deletes the message.

### Transcripts
- Upon interacting with Move Ticket, it will send a transcript to transcript category in config.json.
- The transcript will provide all messages in the ticket alongside the users and the time it was closed.
