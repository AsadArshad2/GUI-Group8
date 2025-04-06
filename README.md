# JavaFX GUI 
<img width="250" alt="Screenshot 2025-03-26 at 11 18 14" src="https://github.com/user-attachments/assets/33a46be4-9efc-4268-91fe-ae2fdab7d605" />

- Team Project Task 🖇️
- For Lancaster Music Hall 🎷
- Worked on by Muhsin & Asad & Daniel 👨‍💻 👨‍💻 👨‍💻
- Utilising SceneBuilder 👷‍♂️ + JavaFX


# Features Checklist

### Bookings & Scheduling
- [ ] Interface to create and manage client bookings
- [ ] Ability to assign rooms/spaces to bookings with:
  - Start/end dates
  - Room configuration/setup requirements
- [ ] Confirm performance times and details
- [ ] Mark whether a booking is confirmed or held
- [ ] Display and update venue calendar availability
- [ ] Restrict conflicting bookings (e.g., same room & time)

### Event & Venue Management
- [x] Daily venue report generator
  - Show all spaces in use, by whom, time, and configuration
- [ ] Real-time venue usage chart to compare with previous years
- [ ] Prevent bookings more than 3 weeks in advance (if by Marketing)
- [ ] Notify Marketing when Operations overrides a Marketing booking

### Seating Configuration
- [ ] Finalize and lock seating layouts before ticket sales
- [ ] Mark restricted-view seats (25% discount)
- [ ] Mark wheelchair-accessible seats clearly
- [ ] Auto-apply pricing adjustments for restricted views

### Financial & Contract Management
- [ ] Interface to create and manage client contracts
- [ ] Input and track:
  - Venue hire cost
  - Ticket sale revenue
  - Revenue split (none, partial, full)
- [ ] Auto-calculate invoice totals
- [ ] Track income from both venue hire and ticket sales
- [ ] Interface to view/print invoice summaries

### Ticketing Data Sync
- [ ] Pull ticket sales data from Box Office
- [ ] Interface to confirm sales totals payable to clients
- [ ] Sync pricing and discount rules with Box Office team
- [ ] Support for agreed max discounts for shows

### Room/Space Information
- [ ] Interface to manage:
  - Room names
  - Capacity
  - Layouts
- [ ] Show which spaces are in use today (for daily sheet)

### Review Management
- [ ] Interface to view and manage venue and show reviews
- [ ] Optional (since API use was dropped): manual input of reviews
- [ ] Option to forward show reviews to clients

### GUI & System Features
- [ ] Print/save PDF versions of:
  - Daily sheets
  - Contracts
  - Invoices
  - Financial reports
- [ ] Interface for Operations Team user logins
- [ ] Navigation between: bookings, calendar, invoices, reviews, reports
- [ ] Alerts for:
  - Double-bookings
  - Overdue contracts
  - Upcoming performances needing setup

### Inter-Team Coordination
- [ ] Send and receive data via internal APIs only (no external APIs)
- [ ] Share:
  - Venue availability
  - Room configurations
  - Performance details
  - Ticket sale summaries
- [ ] Clearly show all data received from Box Office and Marketing teams



# Version History

## 2.1.0 - New feature!
- Daily Sheet section added
- Now able to export daily timetable as a pdf!
- Easy for staff to print out and check todays time
- Implemented using iText7
<img width="500" alt="DailySheet 2 1 0" src="https://github.com/user-attachments/assets/8669e63d-8e6f-4384-a10b-1f4f4daa6024" />
<img width="500" alt="2 1 0 ExportPDF" src="https://github.com/user-attachments/assets/49327c55-1394-47fc-99a2-8c43a1adb7d8" />

### 2.0.1 - Patch
- Ui theme and colour matches with lancasters music hall logo
- Small patch to improve compilation efficency
- Bugs with alignment of buttons have been fixed
- Added Lancaster Music Hall Logo to UI
<img width="500" alt="v2 0 1 Login" src="https://github.com/user-attachments/assets/54c33669-e1b7-41cb-ab4f-15d0b0050896" />
<img width="500" alt="v2 0 1 Menu" src="https://github.com/user-attachments/assets/922952a5-9bab-4867-b8ec-fe27371587ed" />

## 2.0.0 - New version!
- Total Ui overhall
- TouchScreen Freindly Ui
- Menu style instead of tab style
- Simplified sections
- Merged Tab groups into one Menu group
- Booking | Revenue | Review Sections
<img width="400" alt="2 0 0 login" src="https://github.com/user-attachments/assets/eefbd774-03c5-4d49-8096-6ab145be1eac" />
<img width="400" alt="2 0 0 menu" src="https://github.com/user-attachments/assets/24fe2a22-49ca-4f9c-ac98-8e67c9157cd5" />
<img width="400" alt="2 0 0 booking" src="https://github.com/user-attachments/assets/bc4a69a0-e77e-4635-af7b-b9f3c537be0b" />
<img width="400" alt="2 0 0 revenue" src="https://github.com/user-attachments/assets/8809f775-5ff3-4cf1-923b-d2a690b88a4e" />
<img width="400" alt="2 0 0 reviews" src="https://github.com/user-attachments/assets/983721f2-3ebe-4f6c-bb04-0fffeacf9609" />



## Version 1 - Older | Redundant 
<img width="327" alt="Screenshot 2025-03-26 at 11 18 14" src="https://github.com/user-attachments/assets/b98d498f-7c22-45e6-b6fb-d361197fe1ad" />


<img width="955" alt="Screenshot 2025-03-26 at 11 18 57" src="https://github.com/user-attachments/assets/3790fd85-20b4-4916-aa37-c7d911ea5f99" />

