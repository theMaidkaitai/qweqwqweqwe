import {action, makeObservable, observable} from "mobx";


export default class NotesStore {
    _notes = []

    constructor() {
        makeObservable(this, {
            _notes: observable,
            setNotes: action
        });
    }



    setNotes(notes: any) {
        console.log("Store: устанавливаю заметки", notes);
        this._notes = notes || [];
    }

    getNotes() {
        return this._notes;
    }

    get notes() {
        return this._notes;
    }

}