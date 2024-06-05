# US021 - Add a new toDoEntry to the To-Do List

## 4. Tests

**Test 1:** Ensure that a new toDoEntry can be added

    @Test
    void addEntry() {
        ToDoList toDoList = new ToDoList();
        Entry entry = new Entry("Prune trees", "High", "2 hours");
        toDoList.addEntry(entry);
        Entry addedEntry = toDoList.getEntries().get(0);
        assertEquals(entry, addedEntry);
    }

**Test 2:** Ensure that a toDoEntry can be removed

    @Test
    void removeEntry() {
        ToDoList toDoList = new ToDoList();
        Entry entry = new Entry("Prune trees", "High", "2 hours");
        toDoList.addEntry(entry);
        assertEquals(entry, toDoList.getEntries().get(0));
        toDoList.removeEntry(entry);
        assertTrue(toDoList.getEntries().isEmpty());
    }

**Test 3:** Ensure that a toDoEntry cannot be null
    
        @Test
        void addNullEntry() {
            ToDoList toDoList = new ToDoList();
            Entry entry = null;
            assertThrows(IllegalArgumentException.class, () -> {
                toDoList.addEntry(entry);
            });
        }

**Test 4:** Ensure an exception is thrown when a toDoEntry that does not exist is removed

    @Test
    void removeUnexistingEntry() {
        ToDoList toDoList = new ToDoList();
        Entry entry = new Entry("Prune trees", "High", "2 hours");
        assertThrows(IllegalArgumentException.class, () -> {
            toDoList.removeEntry(entry);
        });
    }


## 5. Construction (Implementation)

### Class ToDoListController


    public void addEntryToToDoList(Entry entry, ToDoList toDoList) throws IllegalArgumentException {
        ToDoList old = toDoList.clone();
        toDoList.addEntry(entry);
        toDoListRepository.update(old, toDoList);
    }


### Class ToDoListRepository


    public ToDoList update(ToDoList oldToDoList, ToDoList newToDoList) {
        boolean operationSuccess = false;

        if (toDoLists.contains(oldToDoList)) {
            this.toDoLists.remove(oldToDoList);
            operationSuccess = this.toDoLists.add(newToDoList);
        }

        if (!operationSuccess) {
            throw new IllegalArgumentException("ToDoList not found.");
        }

        return newToDoList;
    }


### Class ToDoList


    public List<Entry> addEntry(Entry entry) {
        if (this.entryList.contains(entry)) {
            throw new IllegalArgumentException("ToDoList already contains the entry");
        }
        if (entry == null) {
            throw new IllegalArgumentException("Entry cannot be null");
        }
        this.entryList.add(entry);

        return this.entryList;
    }


## 6. Integration and Demo

* A new option on the GSM menu options was added.

* For demo purposes some toDoEntries are bootstrapped while system starts.

## 7. Observations

n/a