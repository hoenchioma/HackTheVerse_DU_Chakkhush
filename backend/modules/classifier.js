module.exports = {
    checkUrgent: ({ patientId, type, value }) => {
        switch(type) {
            case 'spo2':
                if(95 < value) {
                    // TODO: Send push notification.
                }
            case 'bp':
                if(180 < value[0] || 120 < value[1]) {
                    // TODO: Send push notification.
                }
            case 'heartRate':
                if(value < 60 || 120 < value) {
                    // TODO: Send push notification.
                }
        }
    }
}